package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverDrivingState implements RoverState {
    /**
     * Begins driving (if not previously started) and continuously checks if the desired distance
     * has been travelled. If so, stops driving, changes the state of the operator's rover to 
     * prepare for setting a message signalling that the rover has stopped driving to send to Earth
     * and then calls to have the message set.
     * 
     * @param context
     */
    @Override
    public void handle(RoverOperator context) {
        try {
            context.getEngineSystem().startDriving();
        }
        catch (Exception e) { } // Catch Exception as specific exception thrown was not specified

        if (context.getTelemetry().getCurrentDistanceDriven() >= 
        context.getTelemetry().getTargetDistanceDriven()) {
            context.getEngineSystem().stopDriving();
            context.setState(new RoverSettingState("D"));
            context.execute();
        }
    }
}