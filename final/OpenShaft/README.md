# `final/OpenShaft` README

Back to [`final/`](../README.md) • Back to the [root README](../../README.md)

This folder contains the main OpenShaft ESP8266 firmware. It is the cleanest example in the repo of the project’s modular firmware structure: startup code in the `.ino` sketch, constants in `constants.h`, motor helpers in `car.h`, Wi‑Fi setup in `wifi.h`, and UDP receive logic in `net.h`.

## Files

| File | Purpose |
|---|---|
| `OpenShaft.ino` | Main firmware loop and control execution |
| `constants.h` | Wi‑Fi credentials, GPIO mapping, command codes, and port settings |
| `car.h` | Helpers for motor enable, steering outputs, drive outputs, and shutdown |
| `net.h` | UDP server start and packet receive helpers |
| `wifi.h` | SoftAP startup helper |

## What this build does

This firmware turns the ESP8266 into a dedicated control endpoint for the RC car:

- starts a Wi‑Fi access point
- initializes steering and drive GPIO pins
- starts a UDP server on port `8888`
- waits for 2-byte control messages
- maps incoming bytes into steering and drivetrain actions
- shuts motors down when control packets stop arriving

## Wi‑Fi identity

This specific variant is configured with:

- **SSID:** `DIVO`
- **Password:** `divo-openshaft`
- **Hostname:** `divo`

If you fork or reuse this build, `constants.h` is the first place to edit.

## GPIO profile

The active GPIO block in `constants.h` is labeled for the **Divo-MOD** build:

- `DRIVE_MOTOR -> D0`
- `FORWARD_GPIO -> D1`
- `REVERSE_GPIO -> D2`
- `TURN_MOTOR -> D7`
- `RIGHT_GPIO -> D6`
- `LEFT_GPIO -> D5`

The same file also contains commented-out mappings for other builds, which suggests this folder was used as a reusable firmware base across multiple toy car conversions.

## Command protocol

This build expects a **2-byte command**:

### First byte: steering / system
- `A` → turn left
- `B` → turn right
- `C` → run full test
- `D` → return steering to neutral / stop turner
- `X` → shutdown motors
- `3` → reset controller

### Second byte: drivetrain / system
- `1` → forward
- `2` → reverse
- `4` → stop drive
- `X` → shutdown motors
- `3` → reset controller

## Firmware flow

### `setup()`
The firmware:

1. starts the access point
2. initializes GPIO state
3. assigns motor enable pins
4. assigns steering pins
5. assigns drive pins
6. enables motor driver channels
7. shuts all motors down as a safe initial state
8. starts the UDP server

### `loop()`
The main loop:

- checks for a UDP packet
- runs `executeControls()` when a packet is received
- shuts motors down when control packets stop coming
- delays briefly to keep the loop stable

This is a clean pattern for a simple, fail-safe RC control stack.

## Notes

- This is a good baseline if you want the simplest modern OpenShaft firmware in the repo.
- If you need PWM-based drive limiting, use [`../OpenShaft_Speed_Locked`](../OpenShaft_Speed_Locked/README.md) instead.
- If you want to drive this firmware from a laptop, pair it with [`../CodeRACER`](../CodeRACER/README.md).
