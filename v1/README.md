# `v1/` README

Back to the [root README](../README.md)

This folder contains the earliest Python control client visible in the repository: `CodeRACER.py`.

## Files

| File | Purpose |
|---|---|
| `CodeRACER.py` | Early UDP control helper for an OpenShaft/RST-style car |

## What is notable about this version

This file looks like an earlier design of the Python control API before the cleaner `final/CodeRACER` version.

Differences include:

- direct helper methods like `turn_left()`, `turn_right()`, `forward()`, and `reverse()`
- an `auto_drive_on()` / `auto_drive_off()` threaded transmission loop
- a different packed command string format
- a different internal state model

## Default protocol pattern

This version uses a packed string:

```python
__COMMANDS = "AQBQQ1Q2"
```

and slices it into pairs:

- `AQ`
- `BQ`
- `Q1`
- `Q2`

That means this early client is **not protocol-compatible by default** with the later `final/CodeRACER` controller, which uses mapped single characters like `A`, `B`, `D`, `5`, `2`, and `4`.

## Why keep this folder documented

Even though it is older, this file is useful because it shows the project’s earlier thinking:

- build a small Python library
- hide UDP details behind a friendly API
- add an “auto drive” mode that continuously transmits the current command state

## Recommendation

Treat this folder as historical or experimental unless you specifically need the older command format.

For current work, prefer:

- [`../final/CodeRACER`](../final/CodeRACER/README.md)
- [`../final/OpenShaft`](../final/OpenShaft/README.md)
