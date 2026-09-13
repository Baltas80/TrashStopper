# Trash Stopper

**Identifica. Advierte. Bloquea.**

Trash Stopper is a privacy-first Android caller identification and spam/fraud protection application created under PAGREY.

## Current implementation

The repository contains a functional Android application built with Kotlin, Jetpack Compose and Room. The current branch includes the main navigation and screen set, local reputation and rule caches, user reports integrated with local number reputation, call screening, safe snapshot storage with checksum/rollback support, and continuous GitHub Actions validation.

### Current capabilities
- Inicio, Actividad, Buscar, Reportar, Protección and Ajustes
- Dark-first premium visual direction with light/dark theme support
- Local number reputation and rule evaluation
- User reports persisted in Room and reflected in the local reputation cache
- Call screening with a fast local decision path
- Local activity and report history
- Safe snapshot staging, checksum validation, activation and rollback
- Android Lint, unit tests, debug APK, release APK and release AAB in CI

## Architecture

- Android / Kotlin
- Jetpack Compose
- Material 3 design system with an original Trash Stopper visual identity
- Room for local data
- CallScreeningService for real-time call decisions
- Local risk/reputation engine with server synchronization points kept modular
- Privacy-first data handling
- No advertising SDK or advertising database is currently integrated

## Reputation data policy

Reputation sources must have clear provenance and licensing compatible with the intended commercial distribution. The project does not scrape websites or integrate proprietary databases without authorization. Until a compatible external source is formally selected, local rules and user reports remain the authoritative local inputs.

## Product roadmap

Planned distribution work includes further synchronization hardening, additional UI states and instrumentation coverage, privacy/distribution documentation, production signing, release validation and Google Play preparation. Premium and Family functionality remains roadmap scope and is not represented by a demo implementation in the current product.

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

Functional alpha / hardening phase. CI has successfully validated the core build pipeline; the remaining distribution blockers are production signing, final privacy/Play declarations, and completion of the remaining hardening and release checks.
