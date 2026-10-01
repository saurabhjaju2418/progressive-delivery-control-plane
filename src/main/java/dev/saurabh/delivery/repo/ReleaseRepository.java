package dev.saurabh.delivery.repo;
import dev.saurabh.delivery.domain.Release;import org.springframework.data.jpa.repository.JpaRepository;import java.util.UUID;
public interface ReleaseRepository extends JpaRepository<Release,UUID>{}
