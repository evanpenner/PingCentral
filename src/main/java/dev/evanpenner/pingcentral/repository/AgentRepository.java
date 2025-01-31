package dev.evanpenner.pingcentral.repository;

import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    @Query("SELECT t FROM Target t JOIN t.assignedAgents a WHERE a.id = :agentId")
    List<Target> getAgentTargets(Long agentId);
}
