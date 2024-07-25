package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;
import edu.curtin.comp2003.operator.utility.Command;

public class RoverIdleState implements RoverState {
    /**
     * Changes the state of the operator's rover based on the specifier of commandStore's next 
     * command to be executed and then calls to have the action associated with that state to be 
     * executed. 
     * 
     * @param context
     */
    @Override
    public void handle(RoverOperator context) {
        Command commandOne = context.getCommandStore().peekCommand();
        switch (commandOne.getSpecifier()) {
            case 'D':
                context.setState(new RoverDrivingState());
                context.execute();
                break;
            case 'T':
                context.setState(new RoverTurningState());
                context.execute();
                break;
            case 'P':
                context.setState(new RoverPhotographingState());
                context.execute();
                break;
            case 'E':
                context.setState(new RoverReportingState());
                context.execute();
                break;
            case 'S':
                context.setState(new RoverAnalysingState());
                context.execute();
                break;
            case '!':
                context.setState(new RoverSettingState("! " + commandOne.toString()));
                context.execute();
                break;
        }
    }
}
