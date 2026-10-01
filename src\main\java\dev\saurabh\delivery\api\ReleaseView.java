package dev.saurabh.delivery.api;
import dev.saurabh.delivery.domain.*;import java.time.Instant;import java.util.*;
public record ReleaseView(UUID id,String service,String environment,String artifactRef,List<Integer> rolloutSteps,int stepIndex,int trafficPercent,ReleaseStatus status,long version,Instant createdAt){
 public static ReleaseView of(Release r){return new ReleaseView(r.id,r.service,r.environment,r.artifactRef,Arrays.stream(r.steps()).boxed().toList(),r.stepIndex,r.trafficPercent,r.status,r.version,r.createdAt);}
}

