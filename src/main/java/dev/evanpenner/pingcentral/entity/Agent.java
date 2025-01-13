package dev.evanpenner.pingcentral.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String os;

    private String version;

    private String hostname;

    private String signedInUsername;

    private boolean verified;

    @JsonIgnore
    @ToString.Exclude
    // bCrypt hash of key sent by client
    private String agentKey;

    @JsonIgnore
    private long lastPollTime;

    @ManyToMany(mappedBy = "assignedAgents", fetch = FetchType.LAZY)
    private List<Target> targets;
}
