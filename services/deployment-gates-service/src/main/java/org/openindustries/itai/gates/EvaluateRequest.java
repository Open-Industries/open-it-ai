package org.openindustries.itai.gates;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "LOW|MEDIUM|HIGH|CRITICAL") String riskTier,
        @Positive int requiredMaxBiasIncidents,
        @Positive long requiredEvalScorePoints) {
}
