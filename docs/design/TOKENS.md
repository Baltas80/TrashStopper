# Trash Stopper — Design Tokens

## Spacing

Base unit: 4dp.

- xs: 4dp
- sm: 8dp
- md: 12dp
- lg: 16dp
- xl: 24dp
- xxl: 32dp
- xxxl: 48dp

## Shape

- Small controls: 10dp
- Cards: 18dp
- Hero surfaces: 24dp
- Full pills: 999dp

## Typography

Material 3 typography roles are used as the implementation baseline.

- Display: protection hero / major status
- Headline: screen titles
- Title: cards and sections
- Body: explanatory content
- Label: controls, metadata and badges

## Semantic risk system

The implementation defines four semantic levels:

- SAFE — low risk / trusted
- CAUTION — moderate risk / review
- HIGH — high risk / likely spam
- CRITICAL — probable fraud or severe risk

Semantic levels require icon + text + visual treatment; color is supplemental only.

## Theme

Light and dark themes share the same semantic structure and spacing. Dark mode is not a simple inversion: surfaces and text hierarchy are tuned independently.

## Interaction

- Primary actions are visually dominant.
- Destructive actions require confirmation when they are reversible or potentially consequential.
- Security decisions remain one-step and low-latency.
- Loading uses skeletons/placeholders where possible.

## Brand color direction

The exact palette will be finalized as part of the visual implementation, but it must support:
- a distinctive primary brand color,
- neutral surfaces,
- clear semantic risk states,
- WCAG-conscious contrast,
- independent light/dark values.
