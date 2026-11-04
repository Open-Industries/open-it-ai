package org.openindustries.itai.quota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenInferenceQuotaRequest(
        @NotBlank String teamCode,
        @PositiveOrZero long budgetUnits) {
}
