package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverTurningState implements RoverState {
    /**
     * Turns at a specified angle, changes the state of the operators's rover to prepare for setting 
     * a message signalling that the rover has turned to send to Earth and then calls to have the
     * message set.
     * 
     * @param context
     */
    
    @Override
    public void handle(RoverOperator context) {
        context.getEngineSystem().turn(context.getTelemetry().getTurnAngle());
        context.setState(new RoverSettingState("T"));
        context.execute();
    }
}