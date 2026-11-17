# Path Rewriting in API Gateway (Banking Platform)

Clients call the public gateway path `/accounts/**`; the gateway rewrites it to the
internal Account Service path `/internal/accounts/**` before forwarding.

| Module | Port | Notes |
|---|---|---|
| `account-service` | 8081 (bound to 127.0.0.1) | Exposes `/internal/accounts` (and `/api/v1/internal/accounts`) |
| `bank-api-gateway` | 8080 | Spring Cloud Gateway, `RewritePath` filter |

## Run (Java 17+, Maven)
```bash
# terminal 1
cd account-service && mvn spring-boot:run
# terminal 2
cd bank-api-gateway && mvn spring-boot:run
```

## Verify
```bash
curl -i http://localhost:8080/accounts          # gateway -> backend /internal/accounts
curl -i http://localhost:8080/accounts/1001     # gateway -> backend /internal/accounts/1001
curl -i http://localhost:8081/accounts          # 404: backend has no public path
```
- The response header `X-Backend-Received-Path: /internal/accounts` shows the rewritten path.
- The Account Service console prints `>>> BACKEND RECEIVED: GET /internal/accounts`.

## Key config (`bank-api-gateway/src/main/resources/application.yml`)
```yaml
filters:
  - RewritePath=/accounts(?<segment>/?.*), /internal/accounts$\{segment}
```
A `StripPrefix=1` + `PrefixPath=/internal/accounts` variant is included (commented) in the file.
