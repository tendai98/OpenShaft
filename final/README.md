# `final/` README

Back to the [root README](../README.md)

The `final/` directory is the most complete working set in this repository. It brings together the main ESP8266 vehicle firmware, a speed-limited firmware variant, an earlier OpenShaft revision, a Python control library, and an ML-assisted browser controller.

## Contents

| Path | Role |
|---|---|
| [`OpenShaft/`](./OpenShaft/README.md) | Main OpenShaft ESP8266 firmware |
| [`OpenShaft_Speed_Locked/`](./OpenShaft_Speed_Locked/README.md) | Speed-limited or PWM-controlled firmware variant |
| [`OpenShaftv1/`](./OpenShaftv1/README.md) | Earlier firmware revision |
| [`CodeRACER/`](./CodeRACER/README.md) | Python UDP control library |
| [`CodeRACER-ML/`](./CodeRACER-ML/README.md) | Node.js + browser ML controller |

## Why this folder matters

If someone wants to understand the repo quickly, this is the best starting point because it contains both sides of the project:

- **Vehicle-side code**: firmware running on the ESP8266
- **Controller-side code**: Python automation and a browser/Node controller

## Recommended reading order

1. [`OpenShaft`](./OpenShaft/README.md)  
   Start here to understand the basic car firmware structure.

2. [`CodeRACER`](./CodeRACER/README.md)  
   Read this next if you want to control the car from Python.

3. [`CodeRACER-ML`](./CodeRACER-ML/README.md)  
   Read this if you want the webcam / model-driven control path.

4. [`OpenShaft_Speed_Locked`](./OpenShaft_Speed_Locked/README.md)  
   Read this if you want the PWM-based drivetrain variant.

5. [`OpenShaftv1`](./OpenShaftv1/README.md)  
   Read this for historical context or legacy command mapping.

## Folder design pattern

Most of the firmware directories in `final/` follow the same modular structure:

- main `.ino` sketch
- `constants.h` for SSID, GPIO, protocol constants
- `car.h` for motor and GPIO helpers
- `wifi.h` for access point setup
- `net.h` for UDP server setup and packet receive logic

That makes it much easier to clone a working car build and adapt it for another chassis or pin layout.

## Good uses for `final/`

Use this folder when you want to:

- flash one of the current firmware variants
- script control from Python
- test a higher-level controller on top of the UDP protocol
- compare how the project evolved from simpler control to more featureful variants
