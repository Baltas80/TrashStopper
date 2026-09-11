# Trash Stopper reputation sources

## Objective

Build a commercial-safe reputation pipeline from sources whose reuse and redistribution rights are explicit. The Android app must consume a compact, versioned local snapshot; raw source files must not be bundled into the APK.

## Verified candidates

| Source | Region | Rights | Role | Status |
|---|---|---|---|---|
| FTC Do Not Call Reported Calls Data | US | Government dataset; legal/commercial review required before redistribution | Primary raw signal | Candidate |
| publicdatadepot/us-robocall-complaints-by-phone-number | US | CC0-1.0 | Aggregated complaint signal | Approved candidate |
| USACallerLookup complaint dataset | US | CC0-1.0 as stated by provider | Bulk lookup/validation signal | Approved candidate |
| mv12star/lista-telefonos-spam | ES | Unlicense repository; upstream provenance must still be audited | Spain candidate | Conditional |
| alterine0101/rcs-spam-blocklist | IN | CC0-1.0 | Secondary RCS signal | Candidate |
| FCC Consumer Complaints | US | Public dataset; redistribution/commercial policy review required | Secondary signal | Candidate |

## Rules

1. A complaint is evidence of a report, not proof that the displayed number is malicious.
2. Caller-ID spoofing means reputation must describe the displayed number, not assert the identity of the caller.
3. Preserve source ID, source version/date, source hash and parser version for every imported batch.
4. Deduplicate by normalized telephone number while retaining a list of contributing sources.
5. Do not import proprietary caller databases by scraping, mirroring, or bypassing access controls.
6. Do not import datasets with non-commercial restrictions into the commercial reputation snapshot.
7. Quarantine ambiguous or provenance-incomplete records instead of silently importing them.
8. The screening service must remain functional when the reputation snapshot is unavailable or stale.
9. Raw datasets stay outside the Android APK. Generate compact shards containing only fields required by the local risk engine.

## Canonical record

```text
phoneNumber
country
category
riskScore
reportCount
verified
firstSeen
lastSeen
sources[]
confidence
campaignId
snapshotVersion
```

## Source manifest

The machine-readable allowlist is `data/source-manifest.json`. A source marked `review-required` must not be shipped in a production snapshot until the legal review is completed.

## Next ingestion stages

1. Download or receive a dated source snapshot.
2. Validate checksum and expected schema.
3. Normalize phone numbers to E.164 where possible.
4. Deduplicate and merge provenance.
5. Apply conservative risk scoring.
6. Quarantine low-confidence/ambiguous records.
7. Generate versioned compact shards and a signed manifest.
8. Run deterministic import tests before publishing a snapshot.
