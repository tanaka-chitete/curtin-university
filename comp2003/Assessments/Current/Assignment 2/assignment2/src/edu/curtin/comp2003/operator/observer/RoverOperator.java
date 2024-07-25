package edu.curtin.comp2003.operator.observer;

import edu.curtin.comp2003.operator.state.RoverIdleState;
import edu.curtin.comp2003.operator.state.RoverState;
import edu.curtin.comp2003.operator.utility.Command;
import edu.curtin.comp2003.operator.utility.CommandStore;
import edu.curtin.comp2003.operator.utility.Telemetry;
import edu.curtin.comp2003.rover.EarthComm;
import edu.curtin.comp2003.rover.EngineSystem;
import edu.curtin.comp2003.rover.Sensors;
import edu.curtin.comp2003.rover.SoilAnalyser;

public class RoverOperator implements CommandStoreObserver {
    private final EarthComm          earthComm;
    private final Sensors            sensors;
    private final VisibilityObserver visibilityObserver; 
    private final EngineSystem       engineSystem;
    private final SoilAnalyser       soilAnalyser;
    private final Telemetry          telemetry;
    private final CommandStore       commandStore; 
    private       RoverState         roverState;

    /** Default Constructor */
    public RoverOperator() {
        earthComm = new EarthComm();
        sensors = new Sensors();
        visibilityObserver = new VisibilityObserver(this);
        visibilityObserver.activate();
        engineSystem = new EngineSystem();
        soilAnalyser = new SoilAnalyser();
        commandStore = new CommandStore(this);
        telemetry = new Telemetry(this);
        roverState = new RoverIdleState();
    }

    /** Run Method */
    public void run() {
        while (true) {
            // Get command, add it to commandStore if it doesn't already have a command in progress
            if (!commandStore.hasCommandA()) {
                String commandAsString = earthComm.pollCommand();
                if (commandAsString != null) {
                    Command commandAsObject = new Command(commandAsString);
                    // Update telemetry if command is either a Drive or Turn command
                    if (commandAsObject.getSpecifier() == 'D') {
                        telemetry.setTargetDistanceDriven(telemetry.getCurrentDistanceDriven() + 
                        commandAsObject.getValue());
                    }
                    else if (commandAsObject.getSpecifier() == 'T') {
                        telemetry.setTurnAngle(commandAsObject.getValue());
                    }
                    commandStore.setCommandA(commandAsObject);
                }
            }
            // Get command, add it to commandStore if it doesn't already have a command on hold
            if (commandStore.hasCommandA() && !commandStore.hasCommandB()) {
                String commandAsString = earthComm.pollCommand();
                if (commandAsString != null) {
                    Command commandAsObject = new Command(commandAsString);
                    // Update telemetry if command is either a Drive or Turn command
                    if (commandAsObject.getSpecifier() == 'D') {
                        telemetry.setTargetDistanceDriven(engineSystem.getDistanceDriven() + 
                        commandAsObject.getValue());
                    }
                    else if (commandAsObject.getSpecifier() == 'T') {
                        telemetry.setTurnAngle(commandAsObject.getValue());
                    }
                    commandStore.setCommandB(commandAsObject); 
                }
            }

            // Execute commands in commandStore
            if (commandStore.hasCommandA()) {
                if (commandStore.hasCommandB()) {
                    if (commandStore.commandBIsIndependent()) { 
                        commandStore.prioritiseCommandB();
                        execute();
                    }
                    else if (commandStore.commandBIsOverriding()) {
                        commandStore.shuffle();
                    }
                }

                execute();
            }

            // Sleep a very small amount of time to prevent any commands from executing for too long
            try { Thread.sleep(100); }
            catch (InterruptedException e) { }
        }
    }

    public void setState(RoverState roverState) {
        this.roverState = roverState;
    }

    public EarthComm getEarthComm() {
        return earthComm;
    }

    public Sensors getSensors() {
        return sensors;
    }

    public EngineSystem getEngineSystem() {
        return engineSystem;
    }

    public SoilAnalyser getSoilAnalyser() {
        return soilAnalyser;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public CommandStore getCommandStore() {
        return commandStore;
    }

    /** 
     * Executes the action associated with commandStore's next command to be executed.
     */
    public void execute() {
        roverState.handle(this);
    }

    /**
     * Upon being notified by commandStore that a message is availble, sends the message to earth 
     * (its state at this point is set to RoverSendingMessageState by commandStore).
     */
    @Override
    public void messageAvailable() {
        execute();
    }
}
