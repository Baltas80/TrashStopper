# Open-source acceleration candidates

This document records candidates that can save development time. It does not authorize copying code or importing datasets. Each integration must pass license, provenance, security and maintenance review.

## High priority references

### OpenCallShield
- Source: https://github.com/jhonsu01/OpenCallShield
- License: MIT (repository license)
- Relevance: Kotlin, Compose, Room, WorkManager, CallScreeningService, local spam database and public synchronization.
- Use: architecture/reference; inspect individual files before any reuse.
- Data warning: repository code license does not automatically establish rights for every aggregated data item.

### CallScreener
- Source: https://github.com/keyvisions/CallScreener
- License: GPL-3.0 according to repository metadata.
- Relevance: blacklist/whitelist/regex rules and local screening architecture.
- Use: conceptual reference only unless Trash Stopper's distribution/licensing model is explicitly confirmed compatible with GPL-3.0.
- Warning: do not copy GPL code into the proprietary/commercial app without a deliberate licensing decision.

### CallShield
- Source: https://github.com/SysAdminDoc/CallShield
- Relevance: layered reputation, campaign detection, local ML, incremental data and source manifests.
- Use: architecture/design reference; audit exact current license before code reuse.

### Android Device Streaming / Firebase Test Lab
- Official services, not third-party app code.
- Relevance: real-device coverage without purchasing a large physical-device matrix.
- Use: later for OEM/API regression and release validation.

## Candidates to investigate next

- FilterYou: Compose/Material 3, Hilt and CallScreeningService patterns.
- iOG26: local database, caller ID, privacy and screening patterns.
- area-code-blocker: area-code/prefix blocking concept.
- Globber: local prefix/pattern matching concept.

## Integration rule

Prefer extracting ideas and implementing them in Trash Stopper's own architecture. If code is reused, record:

- exact repository and commit;
- exact files;
- license;
- copyright/NOTICE obligations;
- modifications;
- dependency version;
- security review;
- whether the result can ship on Google Play under Trash Stopper's intended commercial model.

## Priority order

1. Official Android/Jetpack/Gradle capabilities.
2. Mature permissive-license libraries (Apache-2.0/MIT/BSD) where appropriate.
3. High-quality open-source reference implementations.
4. Public datasets with explicit redistribution/commercial rights.
5. Proprietary APIs only where the time saved justifies dependency and legal cost.

Do not add a dependency merely because it exists. The dependency must remove meaningful work or risk.
