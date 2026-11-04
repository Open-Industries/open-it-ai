# Notification Service

Bounded context: **Notifications** — shared platform pattern. Channel-agnostic dispatch
(EMAIL/SMS/PUSH) driven by domain events; providers are outbound adapters selected by
configuration. Example templates in this core: GOVERNANCE_NOTICE, DRIFT_WARNING, BUDGET_ALERT.

| Item | Value |
|---|---|
| Port | `8085` (`HTTP_PORT`) |
| Base path | `/api/v1/notifications` |
| Owns data | Notification requests and delivery trail |
