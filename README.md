# Trash Stopper

**Identifica. Advierte. Bloquea.**

Trash Stopper is a privacy-first Android caller identification and spam/fraud protection application created under PAGREY.

## Product direction

The project is being designed visually first. Functionality is implemented behind the completed product and design system.

### Current implementation
- Material 3 light/dark UI foundation
- Home, Activity, Search, Protection and Settings surfaces
- Premium and Family product surfaces
- Modular advertising surface
- Android call-screening service skeleton
- Local-first Room data layer in implementation branch
- Automated Android CI with debug APK artifact

### Planned capabilities
- Caller identification
- Spam and fraud detection
- Call screening and blocking
- Number lookup and reputation
- Community reports
- Offline/local protection
- Protection rules and whitelist
- Activity and statistics
- Premium and Family plans
- Advertising support for the Free tier through a modular `AdBanner` component
- Spanish-first UX with internationalization planned

## Architecture direction

- Android / Kotlin
- Jetpack Compose
- Material 3 design system with an original Trash Stopper visual identity
- Room for local data
- CallScreeningService for real-time call decisions
- Local risk/reputation engine with server synchronization
- Privacy-first data handling
- Modular monetization and advertising layer

## Design-first workflow

1. Brand and visual identity
2. Design system
3. Complete screen set and states
4. User flows
5. Functional implementation
6. Integration
7. Testing and hardening
8. APK/AAB and Google Play preparation

## Product tiers

- **Free:** identification, basic protection, lookup, reporting, local protection and advertising where appropriate.
- **Premium:** advanced automatic protection, enhanced reputation updates, predictive protection and ad-free experience.
- **Family:** protection for multiple family members/devices and no advertising.
