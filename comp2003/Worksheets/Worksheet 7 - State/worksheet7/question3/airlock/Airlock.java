package question3.airlock;

import java.util.Timer;
import java.util.TimerTask;

class Airlock {
    /** Nested Classes */

    private class Door { 
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

    private class Sensor extends TimerTask {
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

    private class Pump {
        /** Setters */

        public void beginExtraction() {
            while (pressurised) {
                try {
                    pressure--;
                    Thread.sleep(100);
                    if (pressure < 5.0) {
                        pressurised = false;
                    }
                }
                catch (InterruptedException e) { }
            }
        }

        public void beginReturn() {
            while (!pressurised) {
                try {
                    pressure++;
                    Thread.sleep(100);
                    if (pressure > 90.0) {
                        pressurised = true;
                    }
                }
                catch (InterruptedException e) { }
            }
        }
    }

    /** Fields */

    private final Door     outerDoor;
    private final Door     innerDoor;
    private final Sensor   sensor;
    private final Pump     pump;
    private       double   pressure;    // Auto-initialised to 0.0
    private       boolean  pressurised; // Auto-initialised to false

    /** Default Constructor */

    public Airlock() {
        outerDoor = new Door();
        innerDoor = new Door();
        sensor = new Sensor();
        pump = new Pump();

        sensor.start();
    }

    /** Setters */

    public void pressurise() {
        if (!pressurised) {
            pump.beginReturn();
        }
    }

    public void depressurise() {
        if (pressurised) {
            pump.beginExtraction();
        }
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
}