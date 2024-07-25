package edu.curtin.comp2003.rover;

public class Sensors {
    /** Performs a temperature reading and returns the result in °C. */
    public double readTemperature() { return 0.0; }

    /** Performs a visibility reading and returns the result in km. */
    public double readVisibility() { return 0.0; }

    /** Performs a light-level reading, and returns the result in lux (units). */
    public double readLightLevel() { return 0.0; }

    /** Takes a photo and returns the binary data making up the image. */
    public byte[] takePhoto() { return new byte[0]; }
}
