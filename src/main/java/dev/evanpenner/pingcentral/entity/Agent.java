package dev.evanpenner.pingcentral.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agents")
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

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", os='" + os + '\'' +
                ", version='" + version + '\'' +
                ", hostname='" + hostname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return getId() == agent.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
