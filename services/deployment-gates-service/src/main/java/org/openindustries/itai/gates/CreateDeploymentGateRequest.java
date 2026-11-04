package org.openindustries.itai.gates;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateDeploymentGateRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "LOW|MEDIUM|HIGH|CRITICAL") String riskTier,
        @Positive long evalScorePoints,
        @Positive int maxBiasIncidents,
        @Min(1) @Max(100) int priority) {
}
