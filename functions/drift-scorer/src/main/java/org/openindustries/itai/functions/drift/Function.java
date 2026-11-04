package org.openindustries.itai.functions.drift;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores model drift signals (data drift, quality drop, latency growth) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String modelId, int driftIndex, int qualityDropIndex, int latencyIndex) {
    }

    public record Score(String modelId, int score, String decision, String rationale) {
    }

    @Funq
    public Score driftScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.driftIndex() >= 70) {
            score += 40;
            why.append("driftIndex >= 70; ");
        }
        if (event.qualityDropIndex() >= 60) {
            score += 30;
            why.append("qualityDropIndex >= 60; ");
        }
        if (event.latencyIndex() >= 50) {
            score += 30;
            why.append("latencyIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.modelId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
