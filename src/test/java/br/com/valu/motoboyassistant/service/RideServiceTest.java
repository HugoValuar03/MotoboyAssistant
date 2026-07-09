package br.com.valu.motoboyassistant.service;

import br.com.valu.motoboyassistant.domain.Ride;
import br.com.valu.motoboyassistant.domain.RidePlatform;
import br.com.valu.motoboyassistant.dto.RideCreateRequest;
import br.com.valu.motoboyassistant.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    @Test
    void shouldCreateRideAndCalculateValuePerKm() {
        var request = new RideCreateRequest(
                RidePlatform.UBER,
                new BigDecimal("12.50"),
                new BigDecimal("37.50"),
                LocalDateTime.of(2026, 6, 27, 10, 0),
                "Corrida aeroporto",
                new BigDecimal("0"),
                new BigDecimal("2.30"));

        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = rideService.create(request);

        assertThat(response.platform()).isEqualTo(RidePlatform.UBER);
        assertThat(response.valuePerKm()).isEqualByComparingTo("3.00");
    }

    @Test
    void shouldReturnSummaryForSavedRides() {
        when(rideRepository.findAll()).thenReturn(List.of(
                new Ride(
                        RidePlatform.UBER,
                        new BigDecimal("5.00"),
                        new BigDecimal("25.00"),
                        LocalDateTime.now(),
                        null,
                        new BigDecimal("30"),
                        new BigDecimal("0")),
                new Ride(RidePlatform.IFOOD,
                        new BigDecimal("10.00"),
                        new BigDecimal("20.00"),
                        LocalDateTime.now(),
                        null,
                        new BigDecimal("30"),
                        new BigDecimal("0"))));

        var summary = rideService.summary();

        assertThat(summary.totalRides()).isEqualTo(2);
        assertThat(summary.totalDistanceKm()).isEqualByComparingTo("15.00");
        assertThat(summary.totalValue()).isEqualByComparingTo("105.00");
        assertThat(summary.averageValuePerKm()).isEqualByComparingTo("7");
    }

    @Test
    void shouldThrowWhenRideDoesNotExist() {
        var id = UUID.randomUUID();

        when(rideRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rideService.findById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Corrida não encontrada");
    }
}
