package dev.evanpenner.pingcentral.service;

import dev.evanpenner.pingcentral.api.AgentConnector;
import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AgentService {
    private final AgentRepository agentRepository;
    private final Logger logger = LoggerFactory.getLogger(AgentService.class);
    // bCrypt Password Encoder
    private final PasswordEncoder passwordEncoder;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
}
