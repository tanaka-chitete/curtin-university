package edu.curtin.comp2003.operator.observer;

import java.util.Timer;
import java.util.TimerTask;

import edu.curtin.comp2003.operator.state.RoverIdleState;
import edu.curtin.comp2003.operator.utility.Command;

public class VisibilityObserver extends TimerTask {
    private final Timer         timer;
    private final RoverOperator roverOperator;

    /** Alternate Constructor */
    public VisibilityObserver(RoverOperator roverOperator) {
        timer = new Timer();
        this.roverOperator = roverOperator;
    }

    public void activate() {
        timer.scheduleAtFixedRate(this, 3, 100);
    }

    /**
     * Periodically observes visibility. If visibility is less than 4.0 or greater than 5.0, adds an
     * emergency command to commandStore to report environmental status, makes the command the new
     * command in progress and then changes the sate of the operator's rover to Idle so that the
     * command is executed in the next iteration of the event loop.
     * 
     * @param context
     */
    @Override
    public void run() {
        double visibility = roverOperator.getSensors().readVisibility();
        if (visibility < 4.0 || visibility > 5.0) {
            roverOperator.getCommandStore().setCommandC(new Command("E"));
            roverOperator.getCommandStore().prioritiseCommandC();
            roverOperator.setState(new RoverIdleState());
        }
    }
}
