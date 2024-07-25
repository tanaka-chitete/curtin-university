package question4.airlock;

import java.util.Timer;
import java.util.TimerTask;

public class Airlock {
    /** Fields */

    private final Door         outerDoor;
    private final Door         innerDoor;
    private final Sensor       sensor;
    private final Pump         pump;
    private       double       pressure;    // Auto-initialised to 0.0
    private       boolean      pressurised; // Auto-initialised to false
    private       AirlockState state;

    /** Default Constructor */

    public Airlock() {
        outerDoor = new Door();
        innerDoor = new Door();
        sensor = new Sensor();
        pump = new Pump();
        state = new Depressurised();

        sensor.start();
    }

    /** Setters */

    public void setState(AirlockState state) {
        this.state = state;
    }

    public void pressurise() {
        state.runPump(this);
    }

    public void depressurise() {
        state.runPump(this);
    }

    public void openInnerDoor() {
        if (!outerDoor.isOpen() && pressurised) {
            innerDoor.open();
        }
    }

    public void openOuterDoor() {
        if (!innerDoor.isOpen() && !pressurised) {
            outerDoor.open();
        }
    }

    public void updatePressure(double pressure) {
        this.pressure = pressure;
    }

    public interface AirlockState {
        public void runPump(Airlock context);
    }

    public class Pressurised implements AirlockState {
        @Override
        public void runPump(Airlock context) {
            context.pump.begin(context);
        }
    }

    public class Depressurised implements AirlockState {
        @Override
        public void runPump(Airlock context) {
            context.pump.begin(context);
        }
    }

    /** Nested Classes */

    public class Door { 
        /** Fields */

        private boolean open;

        /** Default Constructor */

        public Door() {
            open = false;
        }

        /** Setters */

        public void open() {
            open = true;
        }

        /** Getters */

        public boolean isOpen() {
            return open;
        }
    }

    public class Sensor extends TimerTask {
        /** Fields */

        private final Timer timer;

        /** Default Constructor */ 

        public Sensor () {
            timer = new Timer();
        }

        /** Getters */

        public double getPressure() {
            return pressure;
        }

        /** Operators */

        public void start() {
            timer.scheduleAtFixedRate(this, 0, 1_000);
        }

        @Override
        public void run() {
            updatePressure(getPressure());
        }
    }

    public class Pump {
        /** Fields */

        private PumpState state;

        /** Default Constructor */

        public Pump() {
            state = new ExtractedAir();
        }

        /** Setters */

        public void begin(Airlock context) {
            state.begin(context);
        }

        public void setState(PumpState state) {
            this.state = state;
        }
    }

    public interface PumpState {
        public void begin(Airlock context);
    }

    public class ExtractedAir implements PumpState {
        @Override
        public void begin(Airlock context) {
            while (context.pressure <= 90.0) {
                try {
                    context.pressure++;
                    Thread.sleep(100);
                }
                catch (InterruptedException e) { }
            }

            context.pump.setState(new ReturnedAir());
            context.pressurised = false;
        }
    }

    public class ReturnedAir implements PumpState {
        @Override
        public void begin(Airlock context) {
            while (context.pressure > 5.0) {
                try {
                    pressure--;
                    Thread.sleep(100);
                }
                catch (InterruptedException e) { }
            }

            context.pump.setState(new ExtractedAir());
            context.pressurised = true;
        }
    }
}
