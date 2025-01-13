package dev.evanpenner.pingcentral.service;

import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.repository.TargetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TargetService {
    private final TargetRepository targetRepository;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public Optional<Target> getTarget(long id) {
        return targetRepository.findById(id);
    }
}
