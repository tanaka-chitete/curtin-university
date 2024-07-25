package edu.curtin.comp2003.operator.state;

import java.util.Base64;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverPhotographingState implements RoverState {
    /**
     * Takes a photo, changes the state of the operators's rover to prepare for setting the photo as 
     * a response message to send to Earth and then calls for the message to be set.
     * 
     * @param context
     */
    @Override 
    public void handle(RoverOperator context) {
        byte[] photo = context.getSensors().takePhoto();
        context.setState(new RoverSettingState("P " + Base64.getEncoder().encodeToString(photo)));
        context.execute();
    }
}
