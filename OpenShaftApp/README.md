# OpenShaft Android Controller

Android companion app for **OpenShaft**, a programmable Wi-Fi RC car project built around the ESP8266.  
This app provides a simple touch-based remote control interface that sends UDP commands to the car over a local network.

## Overview

The OpenShaft Android app is the handheld control side of the broader OpenShaft RC platform.  
It is designed to connect to the RC car over Wi-Fi, send steering and drive commands in real time, and expose a few utility functions such as test drive, emergency stop, and system reset.

This code represents a lightweight, direct-control interface rather than a full telemetry or autonomous control client. The focus is responsiveness, simplicity, and easy integration with the ESP8266 firmware used in the OpenShaft car.

## Features

- Manual connection to the car using **IP address** and **port**
- Touch controls for:
  - Forward drive
  - Reverse drive
  - Left turn
  - Right turn
  - Stop
  - E-Boost mode
- Utility controls for:
  - Test drive
  - Reset system
- Full-screen control UI
- UDP-based low-overhead command transmission
- Simple visual state changes for pressed controls

## How It Works

The app sends compact **2-byte UDP packets** to the OpenShaft car controller.

The control model is split into two logical channels:

- **Byte 0** → steering-related commands
- **Byte 1** → drive-related commands

A background sender thread keeps checking for active control input and transmits commands when needed.

The app is built for **momentary touch interaction**:

- Pressing a control sends the active command
- Releasing it returns the relevant control channel to neutral or return state

## Command Protocol

### Drive commands
- `0x31` → Forward drive
- `0x32` → Reverse drive
- `0x34` → Neutral
- `0x35` → E-Boost mode

### Steering commands
- `0x41` → Left turn
- `0x42` → Right turn
- `0x44` → Return turner / steering return

### System commands
- `0x58` → Stop motors
- `0x43` → Test drive
- `0x33` → Reset system
- `0x88` → No operation

## Connection Flow

1. Launch the app
2. Enter the target device IP address
3. Enter the UDP port
4. Tap **Connect**
5. Use the on-screen controls to drive the car

Once connected, the app creates a `DatagramSocket` and starts a background transmission thread.

## Project Structure

This Android side is centered around a single main activity:

- `MainActivity.java` – handles:
  - UI setup
  - touch/button listeners
  - UDP connection setup
  - command byte management
  - continuous command sending thread

## Tech Stack

- **Language:** Java
- **Framework:** Android / AndroidX AppCompat
- **Networking:** UDP via `DatagramSocket`
- **Target role:** Real-time remote control client for ESP8266-based RC firmware

## Running the App

### Requirements

- Android Studio
- Android device or emulator
- OpenShaft RC car firmware running on the target ESP8266 device
- Phone and RC car on the same reachable network

### Basic setup

1. Open the Android project in Android Studio
2. Build and install the app on an Android device
3. Make sure the car controller is powered on and connected to Wi-Fi
4. Enter the controller IP and port in the app
5. Connect and test the controls

## Notes on Behavior

- The app currently uses **UDP**, so there is no built-in delivery confirmation
- There is no telemetry/feedback channel in this controller implementation
- Commands are sent only when control flags are active
- Idle behavior is lightweight, with the sender thread sleeping briefly when no controls are being used
- Reset and test-drive commands are one-shot style actions that clear themselves after transmission

## Intended Use

This app is useful for:

- Driving the OpenShaft RC platform manually
- Testing ESP8266 motor control logic
- Rapid prototyping for Wi-Fi robotics control
- Serving as a base for future features such as:
  - speed sliders
  - camera streaming
  - telemetry
  - presets
  - autonomous routines
  - sensor overlays

## Limitations

Current implementation is intentionally simple and has a few limitations:

- No authentication or encryption
- No reconnect strategy
- No command acknowledgement
- No latency statistics or diagnostics
- No telemetry display
- UI and protocol are tightly coupled to the current firmware expectations

## Future Improvements

Potential next steps for the Android controller:

- Persistent connection profiles
- Better error handling and validation
- Telemetry dashboard
- Battery / signal indicators
- Adjustable control sensitivity
- On-screen trims
- Safer failsafe handling
- Modernized architecture using ViewModel / coroutines / structured networking

## Related Project

This Android controller is one part of the larger **OpenShaft** project, which includes:

- ESP8266-based RC vehicle controller firmware
- motor driver integration
- Wi-Fi command handling
- experimental programmable RC vehicle variants

See the main repository README for the broader hardware/firmware side of the project.

## License

This project follows the license used in the main OpenShaft repository.
