# Trash Stopper — Design System

## Brand

**Product:** Trash Stopper  
**Tagline:** Identifica. Advierte. Bloquea.  
**Concept:** guardian of the threshold — every incoming call is evaluated before it reaches the user.

## Design principles

1. Protection must be immediately understandable.
2. Privacy is visible, not hidden in settings.
3. One primary action per screen.
4. Risk is communicated through hierarchy, iconography and text — never color alone.
5. Critical call decisions stay visually uncluttered.
6. Ads never appear inside call-screening decisions or security-critical surfaces.
7. The interface must work equally well in light and dark themes.

## Visual language

- Modern Android / Material 3 foundation, with an original identity.
- Generous spacing and rounded surfaces.
- Strong headline typography, restrained secondary text.
- Shield/stopper visual motif without copying competitor interfaces.
- Status semantics: Safe, Caution, High risk, Critical.
- Spanish-first copy; strings remain externalized for future locales.

## Core navigation

- Inicio
- Actividad
- Buscar
- Protección
- Ajustes

## Required screens

### Onboarding
1. Welcome / brand
2. What Trash Stopper protects
3. Privacy model
4. Caller-ID and screening role explanation
5. Permission/setup checklist
6. Protection active confirmation

### Inicio
- Protection status hero
- Number lookup
- Quick protection controls
- Recent activity
- Reputation database freshness
- Free-tier ad slot below non-critical content

### Actividad
- Chronological call events
- Risk/category filters
- Event detail
- Report / mark safe / block actions
- Empty, loading and offline states

### Buscar
- Search field
- Number normalization
- Reputation result
- Risk score
- Category
- Report count
- Recent reports
- Actions: block, allow, report

### Protección
- Master protection state
- Spam blocking
- Fraud blocking
- Unknown callers
- Robocalls / telemarketing (tier dependent)
- Hidden/international controls where supported
- Whitelist
- Advanced Premium controls

### Ajustes
- Account/plan
- Notifications
- Privacy
- Appearance
- Language
- Database updates
- Support
- About

### Premium
- Value proposition
- Feature comparison
- Monthly/yearly selection
- Restore purchase
- Ad-free state

### Family
- Plan status
- Protected members/devices
- Add member/device flow
- Shared protection policies without invasive monitoring

### Call decision surface
- Caller number/name
- Risk level
- Category
- Reputation/report count
- Compact decision/action controls
- Must remain ad-free and distraction-free

## Component inventory

- BrandMark
- ProtectionStatusCard
- RiskBadge
- RiskScore
- NumberIdentityCard
- SearchNumberField
- ActivityRow
- ProtectionToggle
- RuleChip
- EmptyState
- OfflineBanner
- DatabaseFreshnessIndicator
- PlanBadge
- PremiumFeatureCard
- FamilyMemberCard
- AdBanner
- BottomNavigation
- TopAppBar
- PrimaryAction
- SecondaryAction
- ConfirmationSheet
- ErrorState

## State coverage

Every feature must define: loading, success, empty, error, offline, disabled, permission-required and premium-locked states where applicable.

## Accessibility

- Minimum touch targets follow Android accessibility guidance.
- Text remains readable at enlarged font sizes.
- Risk categories have text/icon labels in addition to color.
- Contrast is validated in both themes.
- Motion is optional and never required to understand a security state.
