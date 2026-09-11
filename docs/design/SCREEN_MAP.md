# Trash Stopper — Screen Map

## Global flow

Launch → onboarding/setup (first run) → Inicio → Activity/Search/Protection/Settings.

Incoming call → CallScreeningService → local decision → system call UI / caller identification → asynchronous reputation update.

## Screen matrix

| Area | Screen | Free | Premium | Family |
|---|---|---|---|---|
| Onboarding | Welcome | ✓ | ✓ | ✓ |
| Onboarding | Privacy | ✓ | ✓ | ✓ |
| Onboarding | Setup/permissions | ✓ | ✓ | ✓ |
| Home | Protection dashboard | ✓ | ✓ | ✓ |
| Home | Number lookup | ✓ | ✓ | ✓ |
| Activity | Call history | ✓ | ✓ | ✓ |
| Activity | Event detail | ✓ | ✓ | ✓ |
| Search | Reputation detail | ✓ | ✓ | ✓ |
| Protection | Basic rules | ✓ | ✓ | ✓ |
| Protection | Advanced automatic rules | Limited | ✓ | ✓ |
| Protection | Whitelist | ✓ | ✓ | ✓ |
| Settings | Privacy | ✓ | ✓ | ✓ |
| Settings | Appearance/language | ✓ | ✓ | ✓ |
| Monetization | Premium | ✓ | — | — |
| Family | Family management | — | — | ✓ |

## Critical states

### Protection active
Hero state communicates that protection is running, with the last database update and quick controls.

### Protection degraded
Explain the exact reason (permission, service disabled, stale database, offline) and provide one direct repair action.

### Suspicious call
Use high-priority risk information, category, confidence and concise recommended action.

### Critical/fraud call
No advertising, no decorative distractions. Present strong warning, block action and report action.

### Offline
Local protection continues. Explain that cached reputation remains active and remote enrichment will resume later.

### Premium locked
Explain what the feature adds, but never obstruct basic protection.

## Copy rules

- Avoid alarmist language for moderate risk.
- Use “Posible spam” / “Riesgo alto” / “Posible fraude” according to evidence.
- Never claim certainty when the reputation model is probabilistic.
- Make report counts and evidence understandable.
