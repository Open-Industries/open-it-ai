# Model Service

Bounded context: **AI Models** — AI model registry with lifecycle governance.

Alignment: ISO/IEC 42001 AI system life cycle. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/models` |
| Owns data | AiModel records |
| Publishes | `model.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/model-service quarkus:dev`.
