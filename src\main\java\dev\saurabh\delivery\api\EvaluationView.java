package dev.saurabh.delivery.api;
import dev.saurabh.delivery.domain.*;import java.time.Instant;import java.util.UUID;
public record EvaluationView(UUID id,Decision decision,String reason,double errorRate,int latencyP95Ms,int sampleCount,Instant evaluatedAt){
 public static EvaluationView of(MetricEvaluation e){return new EvaluationView(e.id,e.decision,e.reason,e.errorRate,e.latencyP95Ms,e.sampleCount,e.evaluatedAt);}
}

