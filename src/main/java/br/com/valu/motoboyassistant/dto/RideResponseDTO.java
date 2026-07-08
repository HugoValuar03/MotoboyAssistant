package br.com.valu.motoboyassistant.dto;

import br.com.valu.motoboyassistant.domain.Ride;
import br.com.valu.motoboyassistant.domain.RidePlatform;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public record RideResponseDTO(
        UUID id,
        RidePlatform platform,
        BigDecimal distanceKm,
        BigDecimal totalValue,
        BigDecimal valuePerKm,
        LocalDateTime occurredAt,
        String notes,
        BigDecimal tip,
        BigDecimal waitingFee
) {

    public static RideResponseDTO valueOf(Ride ride) {
        return new RideResponseDTO(
                ride.getId(),
                ride.getPlatform(),
                ride.getDistanceKm(),
                ride.getTotalValue(),
                ride.getTotalValue().divide(ride.getDistanceKm(), 2, RoundingMode.HALF_UP),
                ride.getOccurredAt(),
                ride.getNotes(),
                ride.getTip(),
                ride.getWaitingFee()
        );
    }
}
