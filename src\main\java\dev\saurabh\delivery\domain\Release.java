package dev.saurabh.delivery.domain;
import jakarta.persistence.*;import java.time.Instant;import java.util.UUID;
@Entity @Table(name="releases",indexes=@Index(name="idx_release_env_created",columnList="environment,created_at"))
public class Release{
 @Id public UUID id;@Column(nullable=false,length=100) public String service;@Column(nullable=false,length=80) public String environment;@Column(name="artifact_ref",nullable=false,length=240) public String artifactRef;
 @Column(name="rollout_steps",nullable=false,length=100) public String rolloutSteps;@Column(name="step_index",nullable=false) public int stepIndex;@Column(name="traffic_percent",nullable=false) public int trafficPercent;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=16) public ReleaseStatus status;@Version public long version;
 @Column(name="created_at",nullable=false) public Instant createdAt;@Column(name="updated_at",nullable=false) public Instant updatedAt;
 protected Release(){}
 public Release(String service,String env,String artifact,String steps,Instant now){id=UUID.randomUUID();this.service=service;environment=env;artifactRef=artifact;rolloutSteps=steps;stepIndex=-1;trafficPercent=0;status=ReleaseStatus.CREATED;createdAt=now;updatedAt=now;}
 public int[] steps(){return java.util.Arrays.stream(rolloutSteps.split(",")).mapToInt(Integer::parseInt).toArray();}
}

