create table releases (
 id uuid primary key, service varchar(100) not null, environment varchar(80) not null,
 artifact_ref varchar(240) not null, rollout_steps varchar(100) not null, step_index integer not null,
 traffic_percent integer not null check(traffic_percent between 0 and 100),
 status varchar(16) not null check(status in ('CREATED','ACTIVE','PAUSED','ROLLED_BACK','COMPLETED')),
 version bigint not null default 0, created_at timestamptz not null, updated_at timestamptz not null
);
create index idx_release_env_created on releases(environment,created_at desc);
create table metric_evaluations (
 id uuid primary key, release_id uuid not null references releases(id) on delete cascade,
 error_rate double precision not null check(error_rate between 0 and 1), latency_p95_ms integer not null check(latency_p95_ms > 0),
 sample_count integer not null check(sample_count > 0), decision varchar(16) not null check(decision in ('ADVANCE','PAUSE','ROLLBACK')),
 reason varchar(500) not null, evaluated_at timestamptz not null
);
create index idx_eval_release_time on metric_evaluations(release_id,evaluated_at desc);

