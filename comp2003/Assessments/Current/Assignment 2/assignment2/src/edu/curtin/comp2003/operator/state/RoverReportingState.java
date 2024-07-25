package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverReportingState implements RoverState {
    /**
     * Reports environmental status, changes the state of the operators's rover to prepare for 
     * setting the status as a response message to send to Earth and then calls for the message to 
     * be set.
     * 
     * @param context
     */
    @Override
    public void handle(RoverOperator context) {
        double temperature = context.getSensors().readTemperature();
        double visibility = context.getSensors().readVisibility();
        double lightLevel = context.getSensors().readLightLevel();
        String report = String.format("%f %f %f", temperature, visibility, lightLevel);
        context.setState(new RoverSettingState("E " + report));
        context.execute();
    }
}
