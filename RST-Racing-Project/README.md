# `RST-Racing-Project` README

Back to the [root README](../README.md)

This folder contains ten RC truck firmware directories:

- `RST-0`
- `RST-1`
- `RST-2`
- `RST-3`
- `RST-4`
- `RST-5`
- `RST-6`
- `RST-7`
- `RST-8`
- `RST-9`

The structure strongly suggests a fleet-style setup where each converted RC truck gets its own firmware folder, likely to preserve per-car identities, pin mappings, or tuning changes.

## What each RST folder contains

The folders shown in this project follow the same pattern as the main OpenShaft firmware builds:

- one `.ino` sketch named after the folder
- `constants.h`
- `car.h`
- `net.h`
- `wifi.h`

That makes every RST directory a self-contained Arduino project.

## Example: `RST-0`

In the checked `RST-0` build:

- **SSID:** `RST-0`
- **Password:** `12345678`
- **Hostname:** `rst-0`

The code also shows:

- UDP server on port `8888`
- 2-byte control packets
- PWM-limited drive through `MAX_PWM_DRIVE`
- support for an e-boost drive mode

## Why this folder matters

This is where the repo starts to look less like a one-off experiment and more like a reusable platform for multiple cars.

Possible reasons to keep separate per-car folders:

- each chassis may need slightly different GPIO mapping
- each car may need its own SSID
- one car may have a better motor or geartrain than another
- testing is easier when each car has a known firmware identity

## Practical advice

If you are trying to work with one of the RST cars:

1. open the matching RST folder
2. check `constants.h` first
3. confirm the SSID, GPIO map, and command constants
4. flash only that build to the target car
5. do not assume a controller from another folder will match automatically

## Documentation idea for the future

This parent folder would benefit from a table like:

| Car | SSID | Password | Pin map | Notes |
|---|---|---|---|---|

That single table would make the entire multi-car setup much easier to manage.
