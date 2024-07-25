package edu.curtin.comp2003.operator.state;

import java.util.Base64;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class RoverAnalysingState implements RoverState {
    /**
     * Begins soil analysis (if not previously started) and continuously polls for a result. If 
     * received, changes the state of the operator's rover to prepare for setting the result as a 
     * response message to send to Earth and then calls to have the message set.
     * 
     * @param context
     */
    @Override 
    public void handle(RoverOperator context) {
        try {
            context.getSoilAnalyser().startAnalysis();
        }
        catch (Exception e) { } // Catch Exception as specific exception thrown was not specified

        byte[] analysis = context.getSoilAnalyser().pollAnalysis();
        if (analysis != null) {
            context.setState(new RoverSettingState("S " + 
            Base64.getEncoder().encodeToString(analysis)));
            context.execute();
        }
    }
}
