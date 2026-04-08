# OpenShaft

OpenShaft is a hardware-and-software project built around modified RC toy cars that were converted from analog radio control to ESP8266-based Wi‑Fi control. In this repository, the core pattern is simple: an ESP8266 creates a Wi‑Fi access point, listens for short UDP control messages on port `8888`, and translates those messages into steering and drive motor outputs.

> This README set is written from the current contents of the repository. It documents what is **actually present in the tree today**: ESP8266 firmware variants, Python control helpers, a browser/Node-based ML controller, a historical `v1` Python client, and image assets.

## What is in this repository?

The repo is organized as a set of related but slightly different control stacks:

- **ESP8266 vehicle firmware** for several car builds and revisions
- **Python control libraries** for sending UDP commands to a car over Wi‑Fi
- **A browser + Node.js controller** that uses a Teachable Machine image model to drive the car
- **A bank of per-car firmware folders** under `RST-Racing-Project`
- **Images and screenshots** showing the cars and app

## Repository map

| Path | What it contains |
|---|---|
| [`final/`](./final/README.md) | The most complete collection of firmware and control variants |
| [`final/OpenShaft/`](./final/OpenShaft/README.md) | Main ESP8266 firmware variant for the OpenShaft car |
| [`final/OpenShaft_Speed_Locked/`](./final/OpenShaft_Speed_Locked/README.md) | ESP8266 firmware with PWM-based speed limiting and an extra boost mode |
| [`final/OpenShaftv1/`](./final/OpenShaftv1/README.md) | Earlier firmware revision for the Veyron-MOD build |
| [`final/CodeRACER/`](./final/CodeRACER/README.md) | Python controller library and sample script |
| [`final/CodeRACER-ML/`](./final/CodeRACER-ML/README.md) | Node.js + browser ML-assisted controller |
| [`RST-Racing-Project/`](./RST-Racing-Project/README.md) | Ten RC truck firmware folders: `RST-0` through `RST-9` |
| [`v1/`](./v1/README.md) | Earliest Python client found in the repo |
| [`images/`](./images/README.md) | Project photos and app screenshot assets |

## How the system works

At a high level, the project follows this loop:

1. Flash one of the ESP8266 firmware variants to a NodeMCU/Wemos-style board.
2. The board starts its own Wi‑Fi access point.
3. A controller sends **2-character UDP messages** to the car on port `8888`.
4. The firmware maps the first character to steering and the second character to drive direction or drive mode.
5. The motor driver toggles steering and drivetrain outputs.

That makes the project easy to experiment with: firmware changes live on the car, and control logic can live in Python, a web app, or any other system that can send UDP packets.

## Variants at a glance

| Variant | Primary purpose | Notes |
|---|---|---|
| [`final/OpenShaft`](./final/OpenShaft/README.md) | Main OpenShaft firmware | Uses modular header files and a straightforward UDP command loop |
| [`final/OpenShaft_Speed_Locked`](./final/OpenShaft_Speed_Locked/README.md) | Speed-limited / PWM drive firmware | Adds PWM drive control and a dedicated boost command |
| [`final/OpenShaftv1`](./final/OpenShaftv1/README.md) | Earlier firmware revision | Simpler command handling and earlier naming |
| [`final/CodeRACER`](./final/CodeRACER/README.md) | Python control API | Good for scripting and automation from a laptop |
| [`final/CodeRACER-ML`](./final/CodeRACER-ML/README.md) | ML-assisted browser controller | Uses a Teachable Machine model in the browser and a Node UDP bridge |
| [`RST-Racing-Project`](./RST-Racing-Project/README.md) | Multi-car fleet-style firmware set | Ten directories that appear to be per-car builds/configs |
| [`v1`](./v1/README.md) | Historical Python client | Earlier protocol and auto-drive thread approach |

## Quick start

### Firmware
1. Choose the firmware variant that matches your RC car wiring.
2. Review the relevant `constants.h` to confirm:
   - SSID / password
   - GPIO assignments
   - command codes
3. Open the sketch in the Arduino IDE.
4. Flash it to the ESP8266 board.
5. Power the RC car and connect your controller device to the car’s Wi‑Fi access point.

Start here:
- [`final/OpenShaft`](./final/OpenShaft/README.md)
- [`final/OpenShaft_Speed_Locked`](./final/OpenShaft_Speed_Locked/README.md)
- [`RST-Racing-Project`](./RST-Racing-Project/README.md)

### Python controller
If you want to drive the car from a laptop or script:

- See [`final/CodeRACER`](./final/CodeRACER/README.md) for the later Python API
- See [`v1`](./v1/README.md) for the older Python client

### Browser / ML controller
If you want a webcam-based controller flow:

- Go to [`final/CodeRACER-ML`](./final/CodeRACER-ML/README.md)

## Command protocol overview

This repository contains **more than one command mapping**, so do not assume every firmware build accepts the same payloads.

Examples found in the tree include:

- Steering codes such as `A` (left), `B` (right), `D` (straight/center)
- Drive codes such as `1`, `2`, `4`, or `5` depending on firmware variant
- Extra commands for test mode, reset, shutdown, or e-boost in some builds

Always check the target folder’s `constants.h` or Python command map before mixing controllers and firmware.

## What is missing or unclear

A few things are worth stating clearly:

- The repository description mentions a **custom Android Java app**, but the current tree does **not** include an Android project.
- There is no top-level wiring diagram, bill of materials, or schematic in the repo yet.
- Several files are compact/minified enough that the intent is readable, but the repo would benefit from more comments and setup notes.

If the Android app lives in another repo, the root README should link to it.

## Suggested next cleanup steps

If you plan to keep developing this repository, the biggest documentation wins would be:

1. Add one wiring diagram per car variant
2. Add photos matched to each firmware folder
3. Add a protocol reference table with exact byte pairs per variant
4. Add an Android app repository link if it exists
5. Standardize naming between `OpenShaft`, `CodeRACER`, and `RST`

## Images

Project images and screenshots live in [`images/`](./images/README.md).

## License

This repository is released under the MIT License. See the license notice already referenced in the original repo README.
