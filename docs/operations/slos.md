# Service Level Objectives

Rolling 30 days, from gateway and service metrics.

| Service | Availability SLO | Latency SLO (p95) | Notes |
|---|---|---|---|
| API Gateway → any service | 99.9% | — | Error budget ~43 min/month |
| model-service | 99.9% | < 300 ms reads | |
| inference-quota-service | 99.9% | < 300 ms reads | |
| deployment-gates-service | 99.9% | < 300 ms reads | |
| dataset-catalog-service | 99.9% | < 300 ms reads | |
| notification-service | 99.9% | < 300 ms reads | |
| Functions | 99.5% | cold start < 3 s | Scale-to-zero by design |
