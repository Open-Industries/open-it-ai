# Inference Quota Service

Bounded context: **Inference Quotas** — Guarded inference/training budgets per team and the usage records against them.

Alignment: AI cost governance (FinOps posture). Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/quotas` |
| Owns data | InferenceQuota accounts, InferenceUsage records |
| Publishes | `quota.consumed`, `quota.insufficient` |

Run locally: `mvn -pl services/inference-quota-service quarkus:dev`.
