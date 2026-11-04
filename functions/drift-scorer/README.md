# Drift Scorer (serverless function)

Quarkus Funqy function on Knative (scale to zero). Scores model drift signals (data drift, quality drop, latency growth) with an auditable rationale.

Invoke: `POST /driftScorer` with the event payload.
