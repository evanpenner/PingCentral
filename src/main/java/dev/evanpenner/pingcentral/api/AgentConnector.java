package dev.evanpenner.pingcentral.api;

import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.service.AgentService;
import dev.evanpenner.pingcentral.service.PingService;
import jakarta.servlet.annotation.WebListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController("/api")
public class AgentConnector {
    private final AgentService agentService;
    private final PingService pingService;
    private final Logger logger = Logger.getLogger(AgentConnector.class.getName());

    public AgentConnector(AgentService agentService, PingService pingService) {
        this.agentService = agentService;
        this.pingService = pingService;
    }

    @PostMapping("/registerAgent")
    public Agent registerAgent(@RequestBody AgentRegistrationRequest agentRequest) {
        return agentService.registerAgent(agentRequest);
    }

    public record AgentRegistrationRequest(String hostname, String os, String version, String agentKey,
                                           String signedInUsername) {

    }

}