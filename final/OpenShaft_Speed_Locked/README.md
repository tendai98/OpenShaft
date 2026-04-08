# `final/OpenShaft_Speed_Locked` README

Back to [`final/`](../README.md) • Back to the [root README](../../README.md)

This folder contains a more featureful firmware variant that adds **PWM-based drivetrain control** and a dedicated **boost-style drive mode** on top of the standard OpenShaft control model.

## Files

| File | Purpose |
|---|---|
| `OpenShaft_Speed_Locked.ino` | Main sketch for the speed-limited / boosted variant |
| `constants.h` | Wi‑Fi credentials, pin map, protocol codes, max PWM, and port |
| `car.h` | Vehicle GPIO and shutdown helpers |
| `net.h` | UDP server helpers |
| `wifi.h` | Access point startup |

## What is different about this build

Compared with the main `OpenShaft` build, this version:

- uses `analogWrite()` for forward and reverse drive output
- defines `MAX_PWM_DRIVE`
- adds an `E_BOOST_DRIVE_MODE`
- still supports shutdown, full test, reset, and turner-centering commands

That makes it better suited for experiments where you want a capped drive mode and a separate higher-output mode.

## Wi‑Fi identity

This variant is configured as:

- **SSID:** `RST-Racing`
- **Password:** `12345678`
- **Hostname:** `rst-1`

## GPIO profile

Active pin mapping in `constants.h`:

- `DRIVE_MOTOR -> D1`
- `FORWARD_GPIO -> D2`
- `REVERSE_GPIO -> D3`
- `TURN_MOTOR -> D0`
- `RIGHT_GPIO -> D6`
- `LEFT_GPIO -> D5`

## Command protocol

Like the other modern variants, this build expects a **2-byte UDP payload**.

### First byte: steering / system
- `A` → left
- `B` → right
- `C` → full test
- `D` → return turner / neutral steering
- `X` → shutdown motors
- `3` → reset controller

### Second byte: drive / system
- `1` → forward using PWM (`analogWrite`)
- `2` → reverse using PWM (`analogWrite`)
- `5` → e-boost / full forward digital mode
- `4` → stop drive
- `X` → shutdown motors
- `3` → reset controller

## Why “speed locked” matters

The key distinction here is that forward and reverse motion are not only on/off actions. The sketch explicitly uses a fixed PWM ceiling through `MAX_PWM_DRIVE`, which is useful when:

- the chassis is too twitchy at full power
- you want more stable indoor control
- you are trying to make performance consistent across cars
- you want a normal mode plus a stronger “boost” mode

## Best use cases

Choose this variant when you want:

- safer or more manageable throttle behavior
- a reusable firmware profile for multiple RST-style cars
- a more advanced base than the standard `OpenShaft` folder

If you want the simplest version instead, go back to [`../OpenShaft`](../OpenShaft/README.md).
