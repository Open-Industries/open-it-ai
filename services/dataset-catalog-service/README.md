# Dataset Catalog Service

Bounded context: **Datasets** — Dataset catalogue — metadata only, never content.

Alignment: Dataset governance metadata (ISO/IEC 42001 data posture). Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/datasets` |
| Owns data | Dataset records |

Run locally: `mvn -pl services/dataset-catalog-service quarkus:dev`.
