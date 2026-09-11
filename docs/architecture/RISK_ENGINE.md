# Trash Stopper — Risk Engine

## Decision model
The initial engine is deterministic and explainable. It returns a risk score from 0 to 100, category, confidence and recommended action.

## Signals
- community reports
- report recency
- report frequency
- campaign correlation
- verified identity
- user trust/block rules
- local contact status

## Bands
- 0–24: SAFE
- 25–49: CAUTION
- 50–74: HIGH
- 75–100: CRITICAL

## Safety rule
Remote reputation may enrich a decision but must never be required for the critical incoming-call path. The engine must degrade safely when data is stale or unavailable.
