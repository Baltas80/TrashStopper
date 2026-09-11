# Trash Stopper — Call Screening

## Critical path
`Incoming call → CallScreeningService → local repository → risk engine → response`

The critical decision path must remain local and fast. Network calls are asynchronous enrichment only.

## Android requirements
- API 24+ for CallScreeningService.
- User must grant the supported call-screening role.
- The service must respond within Android's allowed screening window.
- Contacts are treated separately from unknown callers.
- Screening logic must distinguish allow, silence and block outcomes.

## Privacy
The service should inspect only the information required for the decision and avoid sending raw call data to the backend during the critical path.
