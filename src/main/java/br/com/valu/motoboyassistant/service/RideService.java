package br.com.valu.motoboyassistant.service;

import br.com.valu.motoboyassistant.domain.Ride;
import br.com.valu.motoboyassistant.dto.RideCreateRequest;
import br.com.valu.motoboyassistant.dto.RideResponse;
import br.com.valu.motoboyassistant.dto.RideSummaryResponse;
import br.com.valu.motoboyassistant.exception.RideNotFoundException;
import br.com.valu.motoboyassistant.repository.RideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Transactional
    public RideResponse create(RideCreateRequest request) {
        var ride = new Ride(
                request.platform(),
                request.distanceKm(),
                request.totalValue(),
                request.occurredAt(),
                request.notes()
        );

        return RideResponse.from(rideRepository.save(ride));
    }

    @Transactional(readOnly = true)
    public List<RideResponse> findAll() {
        return rideRepository.findAllByOrderByOccurredAtDesc()
                .stream()
                .map(RideResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public RideResponse findById(Long id) {
        return RideResponse.from(rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(id)));
    }

    @Transactional
    public void delete(Long id) {
        if (!rideRepository.existsById(id)) {
            throw new RideNotFoundException(id);
        }
        rideRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public RideSummaryResponse summary() {
        var rides = rideRepository.findAll();
        var totalRides = rides.size();

        BigDecimal totalDistance = rides.stream()
                .map(Ride::getDistanceKm)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalValue = rides.stream()
                .map(Ride::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageValuePerKm = totalDistance.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : totalValue.divide(totalDistance, 2, RoundingMode.HALF_UP);

        return new RideSummaryResponse(
                totalRides,
                totalDistance.setScale(2, RoundingMode.HALF_UP),
                totalValue.setScale(2, RoundingMode.HALF_UP),
                averageValuePerKm
        );
    }
}
