package org.openindustries.itai.dataset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateDatasetRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "TEXT|IMAGE|AUDIO|TABULAR") String dataType,
        @Positive long recordsCount) {
}
