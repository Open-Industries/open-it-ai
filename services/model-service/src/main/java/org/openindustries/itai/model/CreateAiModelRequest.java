package org.openindustries.itai.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateAiModelRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "PYTORCH|TENSORFLOW|ONNX|LLM_FINETUNE") String framework,
        @NotBlank String ownerTeam) {
}
