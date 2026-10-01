package dev.saurabh.delivery.api;
import jakarta.validation.constraints.*;
public record CreateReleaseRequest(@NotBlank @Size(max=100) String service,@NotBlank @Size(max=80) String environment,
 @NotBlank @Size(max=240) String artifactRef,@NotEmpty @Size(max=100) String rolloutSteps){}
