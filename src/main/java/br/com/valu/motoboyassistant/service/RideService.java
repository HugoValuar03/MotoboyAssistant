package br.com.valu.motoboyassistant.service;

import br.com.valu.motoboyassistant.domain.Ride;
import br.com.valu.motoboyassistant.dto.RideCreateRequest;
import br.com.valu.motoboyassistant.dto.RideResponseDTO;
import br.com.valu.motoboyassistant.dto.RideSummaryResponse;
import br.com.valu.motoboyassistant.exception.RideNotFoundException;
import br.com.valu.motoboyassistant.repository.RideRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public List<RideResponseDTO> findAll(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.by(Sort.Direction.DESC, "occurredAt"));

        return rideRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(RideResponseDTO::valueOf)
                .toList();
    }

    public long count() {
        return rideRepository.count();
    }

    @Transactional
    public RideResponseDTO create(RideCreateRequest request) {
        var ride = new Ride(
                request.platform(),
                request.distanceKm(),
                request.totalValue(),
                request.occurredAt(),
                request.notes(),
                request.tip(),
                request.waitingFee());

        return RideResponseDTO.valueOf(rideRepository.save(ride));
    }

    @Transactional
    public RideResponseDTO update(UUID id, RideCreateRequest request) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(id));

        ride.setPlatform(request.platform());
        ride.setDistanceKm(request.distanceKm());
        ride.setTotalValue(request.totalValue());
        ride.setOccurredAt(request.occurredAt());
        ride.setNotes(request.notes());
        ride.setTip(request.tip());
        ride.setWaitingFee(request.waitingFee());

        return RideResponseDTO.valueOf(rideRepository.save(ride));
    }

    @Transactional(readOnly = true)
    public List<RideResponseDTO> findAll() {
        return rideRepository.findAllByOrderByOccurredAtDesc()
                .stream()
                .map(RideResponseDTO::valueOf)
                .toList();
    }

    @Transactional(readOnly = true)
    public RideResponseDTO findById(UUID id) {
        return RideResponseDTO.valueOf(rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(id)));
    }

    @Transactional
    public void delete(UUID id) {
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
                .map(ride -> ride.getTotalValue()
                        .add(ride.getWaitingFee() != null ? ride.getWaitingFee() : BigDecimal.ZERO)
                        .add(ride.getTip() != null ? ride.getTip() : BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageValuePerKm = totalDistance.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : totalValue.divide(totalDistance, 2, RoundingMode.HALF_UP);

        return new RideSummaryResponse(
                totalRides,
                totalDistance.setScale(2, RoundingMode.HALF_UP),
                totalValue.setScale(2, RoundingMode.HALF_UP),
                averageValuePerKm);
    }
}
