package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverSendingState implements RoverState {
    private final String message;
    
    /** Alternate Constructor */
    public RoverSendingState(String message) {
        this.message = message;
    }

    /**
     * Sends a message to Earth and then changes the state of the operator's rover to its idle state
     * as it is no longer executing any action.
     * 
     * @param context
     */
    @Override
    public void handle(RoverOperator context) {
        context.getEarthComm().sendMessage(message);
        context.setState(new RoverIdleState());
    }
}
