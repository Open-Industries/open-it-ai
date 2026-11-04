# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
AI Models → Inference Quotas → Deployment Gates → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `model-service` | 8081 | ISO/IEC 42001 AI system life cycle |
| `inference-quota-service` | 8082 | AI cost governance (FinOps posture) |
| `deployment-gates-service` | 8083 | ISO/IEC 23894 risk treatment; NIST AI RMF Govern posture |
| `dataset-catalog-service` | 8084 | Dataset governance metadata (ISO/IEC 42001 data posture) |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/model-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
