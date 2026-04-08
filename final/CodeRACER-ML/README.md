# `final/CodeRACER-ML` README

Back to [`final/`](../README.md) • Back to the [root README](../../README.md)

This folder contains a small ML-assisted control stack built around:

- **Node.js / Express** as a local web server and UDP bridge
- **a browser client**
- **a Teachable Machine image model**
- **a UDP output path to the RC car**

In plain language: the browser makes predictions from a webcam feed, then the Node server converts those predictions into OpenShaft control packets.

## Files

| Path | Purpose |
|---|---|
| `app.js` | Express server and UDP bridge to the RC car |
| `package.json` | Node package manifest |
| `www/index.html` | Minimal browser entry page |
| `www/js/app.js` | Browser-side ML prediction loop and API caller |
| `www/js/` | Front-end JS dependencies |
| `www/model/` | Teachable Machine model files |

## How it works

### Browser side
The browser code:

1. loads `model.json` and `metadata.json` from `/model/`
2. starts a webcam
3. runs predictions frame by frame
4. converts model classes into booleans like `Left`, `Right`, `Forward`, `Reverse`
5. posts those values to `/api`

### Server side
The Node server:

1. serves static files from `www/`
2. accepts `POST /api`
3. turns the incoming booleans into a 2-character control message
4. sends that UDP command to the RC car at `192.168.4.1:8888`

## Default server behavior

- Static site root: `www`
- HTTP port: `8000`
- RC car UDP target: `192.168.4.1:8888`

## Command generation logic

The Express API converts request booleans into commands like this:

### Steering
- `Left == true` → `A`
- `Right == true` → `B`
- otherwise → `D`

### Drive
- `Forward == true` → `5`
- `Reverse == true` → `2`
- otherwise → `4`

If the final command is not `D4`, it gets transmitted to the RC car.

## Running it

```bash
npm install
node app.js
```

Then open the browser interface on port `8000`.

## Why this folder is interesting

This is one of the most distinctive parts of the repo because it shows the project expanding beyond manual RC control into a higher-level perception loop:

- camera input
- model prediction
- browser logic
- server bridge
- UDP control output
- physical actuation on the car

That is a great foundation for future experiments in gesture control, visual line-following, or low-speed autonomous behavior.

## Caveat

Just like the Python controller, this stack assumes a specific command mapping. Make sure the firmware you flash to the car interprets `A/B/D` and `5/2/4` the same way.
