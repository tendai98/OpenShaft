# `final/CodeRACER` README

Back to [`final/`](../README.md) • Back to the [root README](../../README.md)

This folder contains a lightweight Python control library for sending UDP commands to an OpenShaft-compatible car, plus a tiny example script showing how to use it.

## Files

| File | Purpose |
|---|---|
| `CodeRACER.py` | Python UDP control class |
| `app.py` | Minimal sample script that sends a short movement sequence |

## What this controller is for

This is the laptop-side control layer for the project. Instead of pressing buttons in a phone app, you can send commands directly from Python and automate movement patterns.

That makes it useful for:

- testing a firmware build
- prototyping scripted motion
- building higher-level control logic
- integrating the RC car into another experiment or program

## Default target

By default, the class targets:

- **IP:** `192.168.4.1`
- **Port:** `8888`

That matches the common access-point + UDP model used by the firmware in this repository.

## Command model

The class uses two internal command maps:

### Steering map
- `LEFT -> "A"`
- `RIGHT -> "B"`
- `LINEAR -> "D"`

### Drive map
- `FORWARD -> "5"`
- `REVERSE -> "2"`
- `STOP -> "4"`

The `run()` method combines one steering code and one drive code into a 2-character UDP packet, sends it repeatedly for a short duration, then transmits a stop command.

## Example

```python
from CodeRACER import *

c = CodeRACER("192.168.4.1", 8888)
c.run("linear", "forward", 0.5)
c.run("left", "forward", 1)
c.run("linear", "forward", 1.5)
```

## Important compatibility note

This Python controller is **not universal across every firmware folder** in the repo.

Its default drive map uses `FORWARD = "5"`, which matches variants that interpret `5` as a forward or boost-style drive code. The main `final/OpenShaft` firmware, however, defines forward as `1`.

If your controller does not match your firmware, either:

- edit `CodeRACER.py`, or
- use the provided `map_turner()` and `map_drive()` helpers to remap commands

## Recommended next improvement

A good future upgrade would be to ship this as a small installable package with:

- configurable host and port
- named firmware profiles
- keyboard control
- a protocol compatibility table
