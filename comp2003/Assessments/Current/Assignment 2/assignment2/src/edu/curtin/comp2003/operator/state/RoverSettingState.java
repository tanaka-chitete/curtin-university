package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverSettingState implements RoverState {
    private final String message;

    /** Alternate Constructor */
    public RoverSettingState(String message) {
        this.message = message;
    }

    /**
     * Sets a response message to send to Earth which results in the rover's operator being
     * notified that a message is available which then results in the operator sending that message.
     * 
     * @param context
     */
    @Override
    public void handle(RoverOperator context) {
        context.getCommandStore().setMessage(message);
    }
}
