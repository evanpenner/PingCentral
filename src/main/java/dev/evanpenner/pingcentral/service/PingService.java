package dev.evanpenner.pingcentral.service;

import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.entity.Ping;
import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service
public class PingService {
    private final PingRepository pingRepository;
    private final TargetService targetService;
    private final AgentService agentService;

    public PingService(PingRepository pingRepository, TargetService targetService, AgentService agentService) {
        this.pingRepository = pingRepository;
        this.targetService = targetService;
        this.agentService = agentService;
    }

    public Ping savePing(Ping ping) {
        return pingRepository.save(ping);
    }

    public Ping savePing(long agentId, long timestamp, long targetId, String targetHost, long latency) {
        final Target target = targetService.getTarget(targetId).orElseThrow();
        final Agent agent = agentService.getAgent(agentId).orElseThrow();
        final Ping ping = new Ping(timestamp, target, agent, targetHost, latency);
        return savePing(ping);
    }
}
