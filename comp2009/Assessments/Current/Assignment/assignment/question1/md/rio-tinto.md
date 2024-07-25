# Alternative to Rio Tinto Autonomous Haulage System

## Overview and Purpose of Rio Tinto Autonomous Haulage System
Rio Tinto runs more than 130 autonomous trucks, part of their Autonomous Haulage System (AHS), across their Iron Ore operations. The trucks are operated by a supervisory system and a central controller, rather than a driver. The system uses pre-defined GPS courses to automatically navigate haul roads and intersections and knows actual locations, speeds and directions of all vehicles at all times.

[Source](https://www.riotinto.com/en/about/innovation/automation)

## Overview and Purpose of Alternative
The proposed alternative, named Autonomous Haulage System (Light) (AHSL) serves, overall, the same purpose as AHS. However, AHSL seeks to provide a less resource-intensive alternative for lighter loads travelling shorter distances–substituting road-trains for tip trucks, with the ultimate goals of reducing the allocation of fixed costs for smaller-scale hauls.

## Design of Alternative
The alternative agent discussed is an instance of an autonomous tip-truck (for convenience, an ATT) which operates within AHSL, *not* the entirety of AHSL, itself.

### PEAS Description
#### Performance Measure
- Minimises time spent hauling iron ore
- Minimises distance travelled in hauling iron ore
- Minimises fuel consumption
- Doesn't endanger drivers of manned haulage vehicles
- Doesn't impede upon manned haulage vehicles
- Doesn't impede upon other unmanned vehicles
- Doesn't endanger mine site workers
- Doesn't endanger loading operators
- Doesn't endanger off-loading operators
- Doesn't endanger civilians

#### Environment
- Path between mine site and destination, containing:
    - Living obstacles (mine site workers, loading operators, off-loading operators and civilians)
    - Inanimate obstacles (other vehicles, trees, buildings, fences, walls etc)

#### Actuators
- Accelerate
- Brake
- Steer
- Indicate
- Sound horn
- Off-load (i.e. tip bed)

#### Sensors
- GPS
- Gyroscope (to determine whether load is in danger of spilling)
- Cameras
- Fuel sensors
- Speedometer
- Accelerometer
- Odometer

### Environment
#### Fully Observable, Partially Observable or Unobservable?
Virtually all environments the ATT will be operating are going to be partially observable. This is because the ATT cannot at any given time know, for example, the location of every vehicle along the path between its start and destination sites.

#### Deterministic or Stochastic?
Stochastic, as the ATT agent cannot reliably predict the actions of, for example, motorists.

#### Episodic or Sequential?
Sequential, as the ATT choosing to change into and stay in a particular lane (for a prolonged period of time) might result in the increase in overall travel time as the lane it selected may have a higher percentage of slower-moving vehicles than alternative lanes.

#### Static or Dynamic
Dynamic, as, for example, motorists can continue driving while the ATT is deliberating its next action–like when the ATT is at a stop while the motorists are able to continue driving.

#### Discrete or Continuous?
Both, since the discrete/continous distinction applies to:
1. The *state* of the environment
2. The manner in which *time* is handled
3. The *percepts* and *actions* of the ATT

Hauling a load from one site to another is a continuous-state and continous-time problem as velocity and location of the ATT sweeps through a range of continous states and do so smoothly with the passage of time. On the other hand, input from digital cameras is discrete, but is typically treated as representing continuously varying aspects of the environment that the ATT is currently operating within.