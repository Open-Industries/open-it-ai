# AI Governance Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares open risk items and documentation coverage against governance targets; one finding per breach.

Invoke: `POST /aiGovernanceEvaluator` with the KPI payload.
