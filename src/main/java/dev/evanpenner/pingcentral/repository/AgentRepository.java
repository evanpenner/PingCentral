package dev.evanpenner.pingcentral.repository;

import dev.evanpenner.pingcentral.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

}
