package dev.evanpenner.pingcentral.service;

import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.repository.TargetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TargetService {
    private final TargetRepository targetRepository;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public Optional<Target> getTarget(long id) {
        return targetRepository.findById(id);
    }

    public Stream<Target> queryTargets(int page, int pageSize) {
        return targetRepository.findAll(PageRequest.of(page, pageSize)).stream();
    }
}
