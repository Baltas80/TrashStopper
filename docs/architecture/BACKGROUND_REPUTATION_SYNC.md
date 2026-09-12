# Background reputation synchronization

## Principle

Trash Stopper must remain fully functional when the network is unavailable. Incoming-call screening consumes only the active local snapshot.

## Background pipeline

```text
periodic scheduler
    -> fetch manifest
    -> verify transport + metadata
    -> download required shards
    -> verify SHA-256
    -> validate schema
    -> stage import
    -> run deterministic integrity checks
    -> atomic activation
    -> retain previous snapshot
```

WorkManager is the preferred Android scheduler for deferrable reliable background work. It must not be invoked from the synchronous call-screening path.

## Failure policy

- Network unavailable: keep current snapshot.
- Server unavailable: keep current snapshot.
- Hash mismatch: reject candidate snapshot.
- Schema mismatch: reject candidate snapshot.
- Import failure: roll back staging only.
- Expired snapshot: continue using it with a stale-state marker rather than disabling protection.
- No snapshot installed: use the local rules and default conservative policy.

## Versioning

The active snapshot has a monotonically increasing `snapshotVersion`. Activation is atomic so the screening engine never sees a partially imported dataset.

## Privacy

The synchronization client must not upload the user's call history, contacts, or phone numbers merely to obtain the public reputation snapshot. Any future user-report submission is a separate, explicit product flow with its own consent and privacy controls.

## Library choice

Use stable AndroidX components. As of September 2026, DataStore 1.2.1 is stable; the 1.3.x line remains alpha. WorkManager 2.11.2 is the stable release identified by the AndroidX release documentation. Avoid introducing alpha dependencies into the production path solely for this feature.
