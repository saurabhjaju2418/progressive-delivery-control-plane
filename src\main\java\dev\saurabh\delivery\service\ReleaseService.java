package dev.saurabh.delivery.service;
import dev.saurabh.delivery.api.*;import dev.saurabh.delivery.domain.*;import dev.saurabh.delivery.repo.*;import jakarta.persistence.EntityNotFoundException;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.time.Instant;import java.util.*;
@Service public class ReleaseService{
 private final ReleaseRepository releases;private final MetricEvaluationRepository evaluations;
 public ReleaseService(ReleaseRepository r,MetricEvaluationRepository e){releases=r;evaluations=e;}
 @Transactional public ReleaseView create(CreateReleaseRequest req){
  int[] steps;try{steps=Arrays.stream(req.rolloutSteps().split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();}catch(Exception ex){throw new IllegalArgumentException("rolloutSteps must be comma-separated percentages");}
  if(steps.length==0||steps[steps.length-1]!=100)throw new IllegalArgumentException("Rollout steps must end at 100 percent");
  int prev=0;for(int step:steps){if(step<=prev||step>100)throw new IllegalArgumentException("Rollout steps must be strictly increasing percentages between 1 and 100");prev=step;}
  return ReleaseView.of(releases.save(new Release(req.service(),req.environment(),req.artifactRef(),String.join(",",Arrays.stream(steps).mapToObj(String::valueOf).toList()),Instant.now())));
 }
 @Transactional public ReleaseView start(UUID id){Release r=load(id);if(r.status!=ReleaseStatus.CREATED)throw new IllegalStateException("Only a newly created release can start");r.status=ReleaseStatus.ACTIVE;return ReleaseView.of(r);}
 @Transactional public EvaluationView evaluate(UUID id,EvaluateMetricsRequest req){
  Release r=load(id);if(r.status!=ReleaseStatus.ACTIVE)throw new IllegalStateException("Metrics can be evaluated only for an active release");
  Decision d;String reason;
  if(req.sampleCount()<100){d=Decision.PAUSE;reason="Insufficient sample volume; hold current traffic until at least 100 requests are observed.";}
  else if(req.errorRate()>=0.05||req.latencyP95Ms()>=1500){d=Decision.ROLLBACK;reason="Error rate or p95 latency breached the configured hard threshold.";}
  else if(req.errorRate()>=0.02||req.latencyP95Ms()>=1000){d=Decision.PAUSE;reason="A soft reliability threshold was exceeded; hold traffic for review.";}
  else{d=Decision.ADVANCE;reason="Observed metrics are within configured thresholds.";}
  var evaluation=evaluations.save(new MetricEvaluation(r,req.errorRate(),req.latencyP95Ms(),req.sampleCount(),d,reason));
  if(d==Decision.ROLLBACK){r.status=ReleaseStatus.ROLLED_BACK;r.trafficPercent=0;}
  else if(d==Decision.PAUSE)r.status=ReleaseStatus.PAUSED;
  else{int[] steps=r.steps();int next=r.stepIndex+1;if(next>=steps.length){r.status=ReleaseStatus.COMPLETED;r.trafficPercent=100;}else{r.stepIndex=next;r.trafficPercent=steps[next];if(r.trafficPercent==100)r.status=ReleaseStatus.COMPLETED;}}
  r.updatedAt=Instant.now();return EvaluationView.of(evaluation);
 }
 @Transactional public ReleaseView resume(UUID id){Release r=load(id);if(r.status!=ReleaseStatus.PAUSED)throw new IllegalStateException("Only paused releases can resume");r.status=ReleaseStatus.ACTIVE;r.updatedAt=Instant.now();return ReleaseView.of(r);}
 @Transactional(readOnly=true) public ReleaseView get(UUID id){return ReleaseView.of(load(id));}
 @Transactional(readOnly=true) public List<EvaluationView> history(UUID id){load(id);return evaluations.findByReleaseIdOrderByEvaluatedAtDesc(id).stream().map(EvaluationView::of).toList();}
 private Release load(UUID id){return releases.findById(id).orElseThrow(()->new EntityNotFoundException("Release not found"));}
}

