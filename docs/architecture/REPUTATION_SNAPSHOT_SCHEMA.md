# Trash Stopper reputation snapshot

## Purpose

Define the compact, deterministic format consumed by the Android client. Raw third-party datasets remain outside the APK and are transformed by an ingestion pipeline before publication.

## Canonical record

Each phone number is represented by a normalized E.164 number where possible.

```text
phoneNumber       string  required
country           string  optional ISO-3166 alpha-2
category          string  optional SPAM | TELEMARKETING | ROBOCALL | SCAM | FRAUD | OTHER
riskScore         int     required 0..100
reportCount       int     required >= 0
verified          bool    required
firstSeen         long    optional epoch milliseconds
lastSeen          long    optional epoch milliseconds
sources           array   required source IDs
confidence        int     required 0..100
campaignId        string  optional
snapshotVersion   string  required
```

## Snapshot envelope

```text
schemaVersion
snapshotVersion
createdAt
validFrom
validUntil
recordCount
contentSha256
sources[]
shards[]
```

Every shard is content-addressed by SHA-256. The manifest must describe the exact source versions and parser version used to produce it.

## Import rules

1. Normalize before deduplication.
2. Merge duplicate numbers instead of overwriting provenance.
3. Never treat a complaint as proof of malicious identity.
4. Preserve every contributing source ID.
5. Quarantine records with incomplete provenance or incompatible redistribution rights.
6. Exclude non-commercial datasets from production snapshots.
7. Reject malformed numbers and impossible country/number combinations.
8. Clamp calculated risk and confidence to 0..100.
9. Require deterministic output for identical input snapshots and parser versions.
10. Verify shard hashes before activation.

## Client activation

The Android application should keep the last known-good snapshot active while a new snapshot is downloaded and validated. A failed download, hash mismatch, schema mismatch, expired manifest, or import error must not disable local call screening.

Activation order:

```text
download
  -> checksum
  -> manifest validation
  -> schema validation
  -> normalization/deduplication validation
  -> import into staging
  -> integrity tests
  -> atomic activation
  -> retain previous snapshot for rollback
```

## Screening constraint

The call-screening path must only consult the already-active local representation. Network operations and snapshot imports belong to background work and must never delay `respondToCall()`.

Android requires an incoming `CallScreeningService` to respond within five seconds and explicitly permits local database lookups while warning against repeated timeout conditions.
