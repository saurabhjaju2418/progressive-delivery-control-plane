package dev.saurabh.delivery.api;
import jakarta.validation.constraints.*;
public record EvaluateMetricsRequest(@DecimalMin("0.0") @DecimalMax("1.0") double errorRate,@Min(1) @Max(600000) int latencyP95Ms,@Min(1) @Max(10000000) int sampleCount){}
