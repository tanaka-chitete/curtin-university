package edu.curtin.comp2003.rover;

public class EngineSystem {

    /**
     * Begins driving forward. The effect is *not* to drive a fixed distance, but to simply start
     * driving. The rover will not stop until the stopDriving() method is subsequently called.
     * 
     * If startDriving() is called while the rover is already driving, it will throw an exception.
     */
    public void startDriving() { }

    /** 
     * Stops driving.
     * 
     * If stopDriving() is called while the rover is already stopped, it will throw an exception.
     */
    public void stopDriving() { }

    /**
     * Immediately turns the rover by the specified angle anticlockwise (negative for clockwise).
     */
    public void turn(double angle) { }

    /**
     * Returns the total distance that the rover has ever driven, since it first landed on Mars.
     * This figure is never reset. It remains constant while the rover is stoped, and increases
     * while the rover is driving.
     */    
    public double getDistanceDriven() { return 0.0; }
}