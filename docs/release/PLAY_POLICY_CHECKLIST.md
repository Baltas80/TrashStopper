# Trash Stopper — Google Play policy checklist

## Caller ID / spam protection

Trash Stopper's primary function is caller identification, spam/fraud detection and call screening. The implementation uses Android `CallScreeningService` and the user-selected `ROLE_CALL_SCREENING` role rather than attempting to read the call log as a substitute for screening.

## Call Log / SMS permissions

- Do not add `READ_CALL_LOG`, `WRITE_CALL_LOG` or `PROCESS_OUTGOING_CALLS` unless a later implementation has a documented, policy-eligible core use and the Play declaration has been reviewed.
- Do not use call-log data for advertising, profiling or unrelated analytics.
- Prefer Telecom/CallScreeningService APIs over historical call-log access.

## Contacts

`READ_CONTACTS` is currently declared because Android CallScreeningService only supplies non-contact calls to the screening service unless contact access is granted. This permission must remain limited to caller-protection behavior and must not be used for advertising or unrelated profiling.

For the Android 17 / API 37 target transition, reassess whether the Android Contact Picker can replace broad contact access for every user-facing feature that needs selected contacts. Google Play's new Contacts Permissions policy takes effect on 2027-01-27 for apps targeting API 37+.

## Screening service requirements

- Respond synchronously and locally to incoming calls.
- Keep `respondToCall()` within Android's 5-second screening deadline.
- Network availability must never be required for the decision path.
- Keep the local reputation cache small enough for fast lookup.
- Persist call-event telemetry asynchronously after the Telecom response.

## Privacy

- Request sensitive permissions incrementally and explain their purpose.
- Never transmit contacts or call information to advertising systems.
- Keep reputation data and user rules separated from analytics/ad data.
- Document data retention, deletion and export behavior before production release.

## Release gate

Before Play submission:

1. Inspect the final merged manifest.
2. Review every sensitive permission against the current Play policy.
3. Complete the Play Permissions Declaration where required.
4. Verify the Data Safety form against the actual implementation.
5. Verify privacy-policy statements against actual network/data behavior.
6. Run release build and automated tests.
7. Validate screening behavior on physical Android devices.
8. Record the exact target SDK and policy deadline applicable to the release.
