package dev.evanpenner.pingcentral.service;

import dev.evanpenner.pingcentral.api.AgentConnector;
import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class AgentService {
    private final AgentRepository agentRepository;
    private final Logger logger = LoggerFactory.getLogger(AgentService.class);
    // bCrypt Password Encoder
    private final PasswordEncoder passwordEncoder;
    private final TargetService targetService;

    public AgentService(AgentRepository agentRepository, TargetService targetService) {
        this.agentRepository = agentRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.targetService = targetService;
    }

    public Optional<Agent> getAgent(long id) {
        return agentRepository.findById(id);
    }

    public Agent registerAgent(AgentConnector.AgentRegistrationRequest agentRequest) {
        Agent agent = new Agent();
        agent.
                setAgentKey(
                        passwordEncoder.encode(
                                agentRequest.agentKey()
                        )
                );
        agent.setVerified(false);
        agent.setLastPollTime(System.currentTimeMillis());
        agent.setName(agentRequest.hostname());
        agent.setHostname(agentRequest.hostname());
        agent.setOs(agentRequest.os());
        agent.setVersion(agentRequest.version());
        logger.info("Registering agent: " + agent);
        return agentRepository.save(agent);
    }

    public Stream<Agent> pageAgents(int page, int pageSize) {
        return agentRepository.findAll(PageRequest.of(page, pageSize)).stream();
    }

    public Agent saveAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public Agent verifyAgent(Agent agent) {
        Optional<Agent> optAgent = agentRepository.findById(agent.getId());
        if (optAgent.isPresent()) {
            Agent ag = optAgent.get();
            ag.setVerified(true);
            return agentRepository.save(ag);
        }
        // agent doesn't exist??
        return null;
    }

    public Agent unverifyAgent(Agent agent) {
        Optional<Agent> optAgent = agentRepository.findById(agent.getId());
        if (optAgent.isPresent()) {
            Agent ag = optAgent.get();
            ag.setVerified(false);
            return agentRepository.save(ag);
        }
        // agent doesn't exist??
        return null;
    }

    public boolean authenticateAgent(String agentKey, Long agentId) {
        Optional<Agent> optAgent = agentRepository.findById(agentId);
        if (optAgent.isPresent()) {
            Agent ag = optAgent.get();
            if (ag.isVerified())
                return passwordEncoder.matches(agentKey, ag.getAgentKey());
        }
        return false;
    }

    public List<Target> getAgentTargets(Long agentId) {
        return agentRepository.getAgentTargets(agentId);
    }
}
