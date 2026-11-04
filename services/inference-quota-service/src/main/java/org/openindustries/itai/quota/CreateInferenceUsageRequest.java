package org.openindustries.itai.quota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateInferenceUsageRequest(
        @NotBlank @Pattern(regexp = "COMPLETION|EMBEDDING|VISION|TRAINING") String usageType,
        @Positive long units) {
}
