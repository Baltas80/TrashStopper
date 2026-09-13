# Trash Stopper

**Identifica. Advierte. Bloquea.**

Trash Stopper is a privacy-first Android caller identification and spam/fraud protection application created under PAGREY.

## Current implementation

The repository contains a functional Android application built with Kotlin, Jetpack Compose and Room. The current branch includes the main navigation and screen set, local reputation and rule caches, user reports integrated with local number reputation, call screening, safe snapshot storage with checksum/rollback support, scheduled reputation synchronization, and continuous GitHub Actions validation.

### Current capabilities
- Inicio, Actividad, Buscar, Reportar, Protección and Ajustes
- Dark-first premium visual direction with light/dark theme support
- Local number reputation and rule evaluation
- User reports persisted in Room and reflected in the local reputation cache
- Call screening with a fast local decision path
- Local activity and report history
- Safe snapshot staging, checksum validation, activation and rollback
- Scheduled 24-hour reputation synchronization when network connectivity is available
- Android Lint, unit tests, debug APK, release APK and release AAB in CI

## Architecture

- Android / Kotlin
- Jetpack Compose
- Material 3 design system with an original Trash Stopper visual identity
- Room for local data
- WorkManager for scheduled reputation synchronization
- CallScreeningService for real-time call decisions
- Local risk/reputation engine with modular synchronization
- Privacy-first data handling
- No advertising SDK or advertising database is currently integrated

## Reputation data policy

Trash Stopper currently uses the public `mv12star/lista-telefonos-spam` Spain spam-number dataset as its external reputation source. The upstream GitHub repository is marked **Unlicense**, which is compatible with commercial redistribution subject to the upstream license terms. The application consumes the published list over HTTPS and does not scrape web pages or integrate proprietary databases without authorization.

The source remains an external dependency and can change independently of Trash Stopper. Before each production release, its current license/provenance should be revalidated. User reports and local rules remain locally authoritative and are merged with the external snapshot rather than being discarded.

## Product roadmap

Remaining distribution work includes further synchronization hardening, additional UI states and instrumentation coverage, privacy/distribution documentation, production signing, release validation and Google Play preparation. Premium and Family functionality remains roadmap scope and is not represented by a demo implementation in the current product.

## Design priorities

- Elegant dark mode with elevated surfaces and clear hierarchy
- Consistent light/dark rendering
- Explicit loading, empty and error states
- Fast, understandable call-screening decisions
- Accessible navigation and readable reporting/search flows
- Minimal permissions and privacy-preserving local processing

## Validation

GitHub Actions validates the project with Android Lint, unit tests, debug APK assembly, release APK assembly, release AAB generation and SHA-256 checksums. A green CI build is required before treating generated release artifacts as validated distribution candidates.

Production signing is intentionally kept outside the repository; no private signing key is committed to source control.

## Status

Functional alpha / hardening phase. CI is validating the latest report-deduplication changes; the remaining distribution blockers are production signing, final privacy/Play declarations, and completion of the remaining hardening and release checks.
