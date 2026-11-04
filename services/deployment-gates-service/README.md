# Deployment Gates Service

Bounded context: **Deployment Gates** — Risk-tiered deployment gates: evaluation-score floors and incident envelopes, with reasons.

Alignment: ISO/IEC 23894 risk treatment; NIST AI RMF Govern posture. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/deployment-gates` |
| Owns data | DeploymentGate rules |
| Publishes | `gates.activated` |

Run locally: `mvn -pl services/deployment-gates-service quarkus:dev`.
