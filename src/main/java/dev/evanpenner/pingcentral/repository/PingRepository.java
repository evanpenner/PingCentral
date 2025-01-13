package dev.evanpenner.pingcentral.repository;

import dev.evanpenner.pingcentral.entity.Ping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PingRepository extends JpaRepository<Ping, Long> {
    
}
