package org.openindustries.itai.gates;

import java.util.UUID;

/**
 * DeploymentGate aggregate — a decision rule of this core: for one riskTier, it guarantees a
 * capacity (evalScorePoints) and a threshold (maxBiasIncidents), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class DeploymentGate {
    public UUID id;
    public String name;
    public String riskTier;
    public long evalScorePoints;
    public int maxBiasIncidents;
    public int priority;
    public String status;

    public static DeploymentGate create(String name, String riskTier, long evalScorePoints, int maxBiasIncidents, int priority) {
        DeploymentGate r = new DeploymentGate();
        r.id = UUID.randomUUID();
        r.name = name;
        r.riskTier = riskTier;
        r.evalScorePoints = evalScorePoints;
        r.maxBiasIncidents = maxBiasIncidents;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
