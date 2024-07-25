# Alternative to Boeing Airpower Teaming System

## Overview and Purpose of Boeing Airpower Teaming System
Boeing Airpower Teaming System (ATS) is a stealth, multirole, unmanned aerial vehicle designed to act as a force multiplier aircraft capable of flying alongside manned aircraft for support and performing autonomous missions independently using artificial intelligence. 

[Source](http://www.boeing.com/defense/airpower-teaming-system/)

## Overview and Purpose of Alternative
The proposed alternative, named Airpower Teaming System (Helico) (ATSH) serves, overall, the same purpose as ATS. However, ATSH seeks to be more effective in lower speed, low altitute environments involving building-to-building surveillance and CQC (close-quarters combat)–environments for which winged-aircrafts are typically not well-suited.

## Design of Alternative
### PEAS Description
#### Performance Measure
- Minimises time spent tracking/surveilling adversaries before engaging in combat
- Minimises ammunition usage in taking down adversaries
- Minimises fuel consumption, both in tracking/surveillance and combat-engagement
- Doesn't endanger manned flying vehicles
- Doesn't impede upon other unmanned vehicles
- Doesn't endanger soldiers
- Doesn't endanger civilians

#### Environment
- Mission location, containing:
    - Living obstacles (soldiers, civilians and animals)
    - Inanimate obstacles (other vehicles, trees, buildings, fences, walls etc)

#### Actuators
- Roll
- Pitch
- Yaw
- Lift
- Thrust
- Shoot

#### Sensors
- GPS
- Gyroscope
- Cameras
- Fuel sensors
- Speedometer
- Accelerometer
- Odometer

### Environment
#### Fully Observable, Partially Observable or Unobservable?
Virtually all environments ATSH will be operating are going to be partially observable. This is
because ATSH cannot at any given time know, for example, the location of every adversary with which
it is engaged in combat.

#### Deterministic or Stochastic?
Stochastic, as the agent cannot reliably predict the actions of its adversaries.

#### Episodic or Sequential?
Sequential, as the agent choosing to engage with a particular adversary might hinder its ability to
engage with another adversary as it may have sustained potentially terminal damage from previous engagements.

#### Static or Dynamic
Dynamic, as the adversaries of ATSH can relocate while ATSH is deliberating its next action, for example.

#### Discrete or Continuous?
Both, since the discrete/continous distinction applies to:
1. The *state* of the environment
2. The manner in which *time* is handled
3. The *percepts* and *actions* of ATSH

Surveilling and, in turn, engaging with adversaries is a continuous-state and continous-time problem as velocity and location of ATSH and its adversaries sweep through a range of continous states and do so smoothly with the passage of time. On the other hand, input from digital cameras is discrete, but is typically treated as representing continuously varying aspects of the environment that ATSH is currently operating within.