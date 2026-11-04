package org.openindustries.itai.gates;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class DeploymentGateRepository {
    private final ConcurrentHashMap<UUID, DeploymentGate> store = new ConcurrentHashMap<>();

    public DeploymentGate save(DeploymentGate rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<DeploymentGate> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<DeploymentGate> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<DeploymentGate> activeFor(String riskTier) {
        return store.values().stream()
                .filter(r -> r.riskTier.equals(riskTier) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
