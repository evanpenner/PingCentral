package dev.evanpenner.pingcentral.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long timestamp;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target;

    private String targetHost;

    // failed packet or packet loss is -1
    private long latency;

    public Ping(long timestamp, Target target, Agent agent, String targetHost, long latency) {
        this.timestamp = timestamp;
        this.target = target;
        this.targetHost = targetHost;
        this.latency = latency;
        this.agent = agent;
    }
}
