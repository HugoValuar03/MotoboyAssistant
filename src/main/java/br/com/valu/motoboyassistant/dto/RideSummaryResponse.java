package br.com.valu.motoboyassistant.dto;

import java.math.BigDecimal;

public record RideSummaryResponse(
        long totalRides,
        BigDecimal totalDistanceKm,
        BigDecimal totalValue,
        BigDecimal averageValuePerKm
) {
}
