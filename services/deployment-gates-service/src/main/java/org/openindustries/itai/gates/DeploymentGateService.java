package org.openindustries.itai.gates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DeploymentGateService {

    @Inject
    DeploymentGateRepository repository;

    public DeploymentGate create(CreateDeploymentGateRequest request) {
        return repository.save(DeploymentGate.create(request.name(), request.riskTier(),
                request.evalScorePoints(), request.maxBiasIncidents(), request.priority()));
    }

    public DeploymentGate get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "DeploymentGate not found: " + id));
    }

    public List<DeploymentGate> list() {
        return repository.findAll();
    }

    public DeploymentGate activate(UUID id) {
        DeploymentGate rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.riskTier());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.riskTier());
        }
        for (DeploymentGate rule : candidates) {
            if (rule.maxBiasIncidents <= request.requiredMaxBiasIncidents()
                    && rule.evalScorePoints >= request.requiredEvalScorePoints()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.riskTier() + " but none meets the requirement");
    }
}
