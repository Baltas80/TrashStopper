# Trash Stopper — Privacy Architecture

## Privacy by design
- Local-first screening.
- Minimal permissions.
- No advertising influence on reputation or call decisions.
- No unnecessary contact upload.
- Clear consent for optional analytics and advertising.
- User controls for local history and rules.

## Data separation
Call-decision data, user preferences, monetization entitlements and optional analytics are separate concerns. A monetization state must never alter the risk score itself.

## Future backend
The API should prefer pseudonymous identifiers and hashed/normalized numbers where the product feature permits it. Retention and deletion policies will be documented before production launch.
