package br.com.valu.motoboyassistant.dto;

import br.com.valu.motoboyassistant.domain.Ride;
import br.com.valu.motoboyassistant.domain.RidePlatform;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RideResponse(
        Long id,
        RidePlatform platform,
        BigDecimal distanceKm,
        BigDecimal totalValue,
        BigDecimal valuePerKm,
        LocalDateTime occurredAt,
        String notes
) {

    public static RideResponse from(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getPlatform(),
                ride.getDistanceKm(),
                ride.getTotalValue(),
                ride.getTotalValue().divide(ride.getDistanceKm(), 2, RoundingMode.HALF_UP),
                ride.getOccurredAt(),
                ride.getNotes()
        );
    }
}
