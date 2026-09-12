# STIR/SHAKEN integration

## Purpose

Trash Stopper treats Android's caller-number verification status as one local signal in the reputation engine. It must never be the sole basis for identifying a caller.

## Platform signal

`Call.Details.getCallerNumberVerificationStatus()` is available from API 30 and exposes three states:

- `VERIFICATION_STATUS_PASSED`: positive authenticity signal.
- `VERIFICATION_STATUS_NOT_VERIFIED`: neutral; the network could not verify the number.
- `VERIFICATION_STATUS_FAILED`: negative signal; the displayed number may be impersonated.

## Decision model

The signal is evaluated locally alongside:

1. Explicit user allow/block rules.
2. Local reputation.
3. Report count and confidence.
4. Category policy.
5. Existing risk score.

A failed verification status should increase risk, but must not automatically classify every failed call as fraud. A passed status should reduce risk modestly, without overriding an explicit user block rule.

## Hot-path constraints

The signal is read directly from `Call.Details`; no network request is allowed in the incoming-call decision path. Android requires `CallScreeningService` to respond within five seconds.

## Compatibility

For API < 30, the verification state is treated as `NOT_VERIFIED`.

## Audit requirements

Persist only the minimum derived decision data required by the product. Do not store raw telecom metadata merely to expose the verification status.

## Test matrix

- API < 30 -> neutral verification.
- API 30+ passed -> positive signal.
- API 30+ not verified -> neutral signal.
- API 30+ failed -> increased risk signal.
- Explicit ALLOW rule overrides reputation risk.
- Explicit BLOCK rule overrides positive verification.
- Failed verification plus high reputation risk can reach BLOCK.
- Screening response remains synchronous and local.
