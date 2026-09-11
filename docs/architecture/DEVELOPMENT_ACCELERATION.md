# Trash Stopper — Development Acceleration

## Priority

Prefer existing reliable **software and data resources** before creating new infrastructure. Reuse open-source libraries, patterns, architectures, components, datasets, tooling, CI/CD and testing infrastructure when their license, provenance, maintenance status and commercial compatibility permit it.

Keep third-party code/data clearly separated from Trash Stopper's own implementation and never mix resources from other PAGREY applications.

## Software-first rule

Hardware is not a development priority for this project. Use the existing development machine and cloud/remote testing resources first. Only consider hardware changes if a verified build, emulator, telecom test, or release-validation requirement cannot be met otherwise.

## Cloud and testing resources

- GitHub Actions: authoritative build/test pipeline.
- Gradle Build Cache: enabled in `gradle.properties` to avoid rebuilding unchanged task outputs.
- Android Device Streaming powered by Firebase: use remote physical devices for additional OEM/API coverage without buying every device.
- Firebase Test Lab: use real-device and Robo/instrumentation testing when the project is ready for instrumentation coverage.
- Android Emulator: use hardware acceleration and multiple API profiles for repeatable local testing.

## Open-source/resource audit rules

Before integrating a third-party resource, record:

1. Name and source URL.
2. Exact version/commit used.
3. License.
4. Whether commercial use is permitted.
5. Whether redistribution is permitted.
6. Attribution/NOTICE requirements.
7. Data provenance, when applicable.
8. Maintenance/activity status.
9. Security concerns and dependency exposure.
10. Why the resource saves work compared with implementing it internally.

## Current high-value references

- OpenCallShield — architectural reference for Kotlin/Compose/Room/CallScreeningService/local spam data.
- CallShield — reference for layered detection, campaign detection, local ML and incremental reputation data.
- CallScreener — reference for caller rules and screening behavior; GPL-3.0 means conceptual reference unless licensing is deliberately compatible.
- Globber — reference for local pattern/prefix rules.
- Android official CallScreeningService/Emulator/Device Streaming documentation — authoritative platform behavior.

These references are not automatically copied into the application. Code and data must be evaluated separately.

## Data sources

Prefer, in order:

1. Trash Stopper's own user reports and derived reputation.
2. Government/public datasets with explicit compatible reuse rights.
3. CC0 or similarly permissive datasets with verified provenance.
4. Open datasets whose commercial redistribution terms have been explicitly verified.
5. Proprietary APIs only when a future commercial agreement justifies the dependency.

Do not scrape or redistribute proprietary caller databases without authorization.

## Multitasking policy

Parallelize independent work where practical:

- UI/design work.
- Screening engine and tests.
- Data/reputation pipeline.
- CI/build optimization.
- Documentation and licensing inventory.
- Device/test matrix preparation.

Do not parallelize conflicting writes to the same repository file or make claims about build/release state until CI or the relevant artifact is verified.
