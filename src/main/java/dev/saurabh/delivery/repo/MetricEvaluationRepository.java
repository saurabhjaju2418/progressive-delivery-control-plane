package dev.saurabh.delivery.repo;
import dev.saurabh.delivery.domain.MetricEvaluation;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;import java.util.UUID;
public interface MetricEvaluationRepository extends JpaRepository<MetricEvaluation,UUID>{List<MetricEvaluation> findByReleaseIdOrderByEvaluatedAtDesc(UUID releaseId);}