package dev.evanpenner.pingcentral.api;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.entity.Ping;
import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.service.AgentService;
import dev.evanpenner.pingcentral.service.PingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@AnonymousAllowed
@RequestMapping("/api/v1")
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


    @GetMapping("/syncAgent/{agentId}")
    public AgentResponse syncAgent(@RequestParam String agentKey, @PathVariable Long agentId, @RequestBody AgentPushUpdateRequest agentPushUpdateRequest) {
        if (!agentService.authenticateAgent(agentKey, agentId)) {
            // TODO: implement logging when authentication fails
            return new AgentResponse(List.of());
        }
        List<PingItem> pingItemList = agentPushUpdateRequest.pings();
        for (PingItem pingItem : pingItemList) {
            pingService.savePing(agentId, pingItem.timestamp(), pingItem.targetId(), pingItem.targetHost(), pingItem.latency());
        }
        return new AgentResponse(agentService.getAgentTargets(agentId));
    }

    public record AgentResponse(List<Target> targets) {

    }

    public record AgentPushUpdateRequest(List<PingItem> pings) {

    }

    public record PingItem(long timestamp, long targetId, String targetHost, long latency) {

    }

    public record AgentRegistrationRequest(String hostname, String os, String version, String agentKey,
                                           String signedInUsername) {

    }

}
