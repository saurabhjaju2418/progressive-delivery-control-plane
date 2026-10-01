<div align="center">

<img src="assets/project-banner.svg" alt="Animated Canopy — Progressive Delivery banner" width="900" />

# Canopy — Progressive Delivery

**Ship in steps. Let service health decide what happens next.**

Java · Spring Boot · Kubernetes · GitOps

![Project status](https://img.shields.io/badge/status-in%20progress-7a8b71)

</div>

## Product scope

Define a staged rollout, watch service indicators, and record every promotion or rollback decision.

## Architecture notes

GitOps-backed desired state; metrics-based analysis gates; policy-driven rollback; immutable release events; role-scoped approvals.

### Data model sketch

    releases(id, service, artifact, strategy, state) · rollout_steps(release_id, weight, duration) · gate_results(release_id, signal, value, decision)

## Stack

Java · Spring Boot · Kubernetes · GitOps

## Build sequence

1. Release and strategy model
2. Canary state machine
3. Metrics gates and rollback
4. GitOps adapter, policy, and audit

## Current status

Public repository with an animated README. Product code is being built incrementally, one project at a time. This page records the planned product boundary and engineering milestones.

## License

MIT.
