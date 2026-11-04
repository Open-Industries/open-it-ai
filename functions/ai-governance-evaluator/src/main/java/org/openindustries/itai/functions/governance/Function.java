package org.openindustries.itai.functions.governance;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares open risk items and documentation coverage against governance targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String modelId, int measuredOpenRiskItems, int targetOpenRiskItems,
                      double measuredDocumentationCoveragePct, double targetDocumentationCoveragePct) {
    }

    public record Verdict(String modelId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict aiGovernanceEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredOpenRiskItems() > kpi.targetOpenRiskItems()) {
            findings.add("openRiskItems " + kpi.measuredOpenRiskItems() + " exceeds target " + kpi.targetOpenRiskItems());
        }
        if (kpi.measuredDocumentationCoveragePct() < kpi.targetDocumentationCoveragePct()) {
            findings.add("documentationCoveragePct " + kpi.measuredDocumentationCoveragePct() + " below target " + kpi.targetDocumentationCoveragePct());
        }
        return new Verdict(kpi.modelId(), findings.isEmpty(), findings);
    }
}
