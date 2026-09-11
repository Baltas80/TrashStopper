# Trash Stopper — Entitlements

## Tiers

### FREE
Caller ID, basic spam/fraud classification, lookup, manual block/allow, reporting, history, offline cached protection, periodic reputation updates, limited advertising.

### PREMIUM
Everything in Free plus advanced automatic blocking, robocall/telemarketing controls, campaign intelligence, enhanced reputation refresh, predictive protection, statistics and ad-free experience.

### FAMILY
Family protection and management across supported devices, shared protection policies and ad-free experience. No invasive monitoring of family members.

### BUSINESS (future)
Verified business identity, logo/name, call reason, business reputation and stronger anti-spoofing capabilities.

## Implementation contract

Entitlements are represented independently from UI. Screens ask for capabilities rather than checking product names directly.

Example capability names:

- `CALLER_ID`
- `BASIC_SPAM_PROTECTION`
- `ADVANCED_AUTO_BLOCK`
- `CAMPAIGN_INTELLIGENCE`
- `PREDICTIVE_PROTECTION`
- `ADVANCED_STATS`
- `ADS_FREE`
- `FAMILY_MANAGEMENT`

This prevents monetization rules from becoming coupled to the design layer.
