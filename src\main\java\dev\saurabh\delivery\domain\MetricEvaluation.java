package dev.saurabh.delivery.domain;
import jakarta.persistence.*;import java.time.Instant;import java.util.UUID;
@Entity @Table(name="metric_evaluations",indexes=@Index(name="idx_eval_release_time",columnList="release_id,evaluated_at"))
public class MetricEvaluation{
 @Id public UUID id;@ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="release_id",nullable=false) public Release release;
 @Column(name="error_rate",nullable=false) public double errorRate;@Column(name="latency_p95_ms",nullable=false) public int latencyP95Ms;
 @Column(name="sample_count",nullable=false) public int sampleCount;@Enumerated(EnumType.STRING) @Column(nullable=false,length=16) public Decision decision;
 @Column(nullable=false,length=500) public String reason;@Column(name="evaluated_at",nullable=false) public Instant evaluatedAt;
 protected MetricEvaluation(){}
 public MetricEvaluation(Release r,double error,int latency,int samples,Decision decision,String reason){id=UUID.randomUUID();release=r;errorRate=error;latencyP95Ms=latency;sampleCount=samples;this.decision=decision;this.reason=reason;evaluatedAt=Instant.now();}
}

