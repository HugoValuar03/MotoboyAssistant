package br.com.valu.motoboyassistant.dto;

import br.com.valu.motoboyassistant.domain.RidePlatform;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideCreateRequest(
        @NotNull RidePlatform platform,
        @NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal distanceKm,
        @NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal totalValue,
        @NotNull LocalDateTime occurredAt,
        @Size(max = 255) String notes,
        @DecimalMin(value = "0.00", inclusive = true) BigDecimal tip,
        @DecimalMin(value = "0.00", inclusive = true) BigDecimal waitingFee) {
}
