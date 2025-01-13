package dev.evanpenner.pingcentral.repository;

import dev.evanpenner.pingcentral.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
}
