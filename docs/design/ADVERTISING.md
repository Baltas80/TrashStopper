# Advertising Design Contract

## Goal

Advertising supports the Free tier without weakening trust or protection.

## Rules

- Ads are enabled only when the active entitlement permits them.
- Premium and Family render no ads.
- No ads in incoming-call screening, fraud warnings, blocking confirmations or other critical security surfaces.
- Reserve stable layout space to prevent content jumping.
- Consent/privacy handling is isolated from UI components.
- Provider implementation is abstracted behind `AdProvider`.
- A no-fill/error state occupies the same reserved area without exposing provider errors.
- Ads must never influence risk decisions, reputation scoring or call actions.

## UI component

`AdBanner(slotId, modifier)` is a presentation component. It receives policy/entitlement state and never decides whether the user is eligible.

## Policy

`AdPolicy` determines whether a slot may render. Inputs include entitlement, consent state, screen safety classification and provider availability.

Conceptual decision:

`adsEnabled = entitlement == FREE && consentAllowsAds && screenAllowsAds && providerAvailable`

## Initial placements

Allowed:
- Home: below primary protection content.
- Activity: between non-critical list sections.
- Search: below reputation result and actions.
- Non-critical settings/help surfaces if later justified.

Forbidden:
- Call screening decision surface.
- Fraud/critical risk warning.
- Permission prompts.
- Blocking/allow confirmation.
- Emergency/support content.

## Product principle

The user must never feel that protection is being traded for advertising.
