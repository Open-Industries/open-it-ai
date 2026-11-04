# Open IT & AI — Reference AI/IT Platform Core

A **standard, generic it services and artificial intelligence core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> inference and training consumption against a team's budget never exceeds that budget — a usage record that would overrun it is rejected (422) and records nothing, so AI spend is always controlled and evidenced.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| ISO/IEC 42001 | AI management systems (AIMS) | `docs/domain/domain-standards.md` mapping; governance evaluator |
| ISO/IEC 23894 | AI risk management | Risk posture in rules and governance docs |
| ISO/IEC 22989 | AI concepts and terminology | Ubiquitous language in the domain model |
| NIST AI RMF | AI risk management framework | Map/Measure/Manage posture complementing ISO mapping |
| ISO/IEC 20000 | IT service management | Service-operation posture in runbook/SLOs |
| ISO/IEC 25059 | Quality model for AI systems | Quality-attributes mapping |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `model-service` | 8081 | ISO/IEC 42001 AI system life cycle | AI model registry with lifecycle governance. |
| `inference-quota-service` | 8082 | AI cost governance (FinOps posture) | Guarded inference/training budgets per team and the usage records against them. |
| `deployment-gates-service` | 8083 | ISO/IEC 23894 risk treatment; NIST AI RMF Govern posture | Risk-tiered deployment gates: evaluation-score floors and incident envelopes, with reasons. |
| `dataset-catalog-service` | 8084 | Dataset governance metadata (ISO/IEC 42001 data posture) | Dataset catalogue — metadata only, never content. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `drift-scorer` | Event / HTTP | Scores model drift signals (data drift, quality drop, latency growth) with an auditable rationale. |
| `ai-governance-evaluator` | Event / HTTP | Compares open risk items and documentation coverage against governance targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — inference and training consumption against a team's budget never exceeds that budget — a usage record that would overrun it is rejected (422) and records nothing, so AI spend is always controlled and evidenced.
3. **Privacy by design** — Prompts and training data are content, not platform data: this core records models, datasets (metadata only), budgets and verdicts — never prompt or payload content. All data is synthetic.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/model-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `deployment-gates-service` context and function configuration |
| Change the guarded capacity semantics | The `inference-quota-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
