# `final/OpenShaftv1` README

Back to [`final/`](../README.md) • Back to the [root README](../../README.md)

This folder contains an earlier OpenShaft firmware revision. It already follows the now-familiar modular layout, but its command handling is simpler than the later variants.

## Files

| File | Purpose |
|---|---|
| `OpenShaftv1.ino` | Main firmware sketch |
| `constants.h` | Early Wi‑Fi, GPIO, and command definitions |
| `car.h` | Vehicle GPIO helpers |
| `net.h` | UDP receive helper |
| `wifi.h` | Access point setup |

## Wi‑Fi identity

This version uses:

- **SSID:** `Veyron-MOD`
- **Password:** `veyron-secure`
- **Hostname:** `veyron`

That suggests this folder was tied to an earlier named vehicle build.

## What is simpler here

Compared with the later `OpenShaft` firmware:

- no explicit shutdown command is handled
- no explicit turner-neutral command is handled
- no explicit stop-drive command is defined
- reset is named `RESET_CAR`
- drive actions are more minimal

This makes `OpenShaftv1` useful as a historical reference for how the control model evolved.

## Command protocol

### First byte
- `A` → left
- `B` → right
- `C` → full test

### Second byte
- `1` → forward
- `2` → reverse
- `3` → reset car

## When to use this folder

Use this folder if you want to:

- understand the earlier design stage
- compare old and new command mappings
- recover support for an older car build
- document the project’s evolution over time

For current work, the more complete starting points are:

- [`../OpenShaft`](../OpenShaft/README.md)
- [`../OpenShaft_Speed_Locked`](../OpenShaft_Speed_Locked/README.md)
