package edu.curtin.comp2003.operator.utility;

import edu.curtin.comp2003.operator.observer.RoverOperator;
import edu.curtin.comp2003.operator.state.RoverIdleState;
import edu.curtin.comp2003.operator.state.RoverSendingState;

public class CommandStore {
    private final RoverOperator roverOperator;
    private       Command       commandA; 
    private       Command       commandB;     
    private       Command       commandC;
    private       String        message;    

    /** Alternate Constructor */
    public CommandStore(RoverOperator roverOperator) {
        this.roverOperator = roverOperator;
        commandA = null;
        commandB = null;
        commandC = null;
        message = null;
    }

    public void setCommandA(Command commandA) {
        this.commandA = commandA;
    }

    public void setCommandB(Command commandB) {
        this.commandB = commandB;
    }

    public void setCommandC(Command commandC) {
        this.commandC = commandC;
    }

    public void setMessage(String message) {
        this.message = message;
        shuffle();
        roverOperator.setState(new RoverSendingState(pollMessage()));
        notifyOperator();
    }

    /** 
     * Prepares to execute the command on hold as the command in progress has finished execution or
     * has been been overridden.
     */
    public void shuffle() {
        commandA = commandB;
        commandB = commandC;
        commandC = null;
        roverOperator.setState(new RoverIdleState());
    }

    /**
     * Prepares to execute the command on hold as it is a state independent command (i.e. 'P', '!'
     * or 'E')
     */
    public void prioritiseCommandB() {
        Command copyOfCommandTwo = commandB;
        commandB = commandA;
        commandA = copyOfCommandTwo;
        roverOperator.setState(new RoverIdleState());
    }

    /** 
     * Prepares to execute the emergency command (resulting from an visibility reading above or 
     * below the prescribed thresholds.
     */
    public void prioritiseCommandC() {
        Command copyOfCommandThree = commandC;
        commandC = commandB;
        commandB = commandA;
        commandA = copyOfCommandThree;
        roverOperator.setState(new RoverIdleState());
    }

    public boolean hasCommandA() {
        return commandA != null;
    }

    public boolean hasCommandB() {
        return commandB != null;
    }

    public boolean commandBIsIndependent() {
        char specifier = commandB.getSpecifier();
        return specifier == 'P' || specifier == 'E' || specifier == '!';
    }

    public boolean commandBIsOverriding() {
        char commandOneSpecifier = commandA.getSpecifier();
        char commandTwoSpecifier = commandB.getSpecifier();
        return commandOneSpecifier == 'D' && commandTwoSpecifier == 'D';
    }

    public String pollMessage() {
        String polledMessage = message;
        message = null;
        return polledMessage;
    }

    public Command peekCommand() {
        return commandA;
    }

    /** 
     * Upon setting a message, notifies the rover's operator that a message is available to send
     * to earth which then results in the operator sending that message.
     */
    public void notifyOperator() {
        roverOperator.messageAvailable();
    }
}
