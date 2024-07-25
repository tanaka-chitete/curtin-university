package edu.curtin.comp2003.operator.utility;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class Telemetry {
    private final RoverOperator roverOperator;
    private       double        targetDistanceDriven;
    private       double        turnAngle;

    /** Alternate Constructor */
    public Telemetry(RoverOperator roverOperator) {
        this.roverOperator = roverOperator;
    }

    public void setTargetDistanceDriven(double targetDistanceDriven) {
        this.targetDistanceDriven = targetDistanceDriven;
    }

    public void setTurnAngle(double turnAngle) {
        this.turnAngle = turnAngle;
    }

    public double getCurrentDistanceDriven() {
        return roverOperator.getEngineSystem().getDistanceDriven();
    }

    public double getTargetDistanceDriven() {
        return targetDistanceDriven;
    }

    public double getTurnAngle() {
        return turnAngle;
    }
}
