public class SecuritySystem implements SensorObserver 
{
    private MotionSensor motionSensor;
    private HeatSensor heatSensor;
    private Alarm alarm;
    private boolean armed;

    public SecuritySystem(MotionSensor inMotionSensor, HeatSensor inHeatSensor, Alarm inAlarm)
    {
        motionSensor = inMotionSensor;
        heatSensor = inHeatSensor;
        motionSensor.addSensorObserver(this);
        heatSensor.addSensorObserver(this);
        alarm = inAlarm;
        armed = false;
    }

    public void setArmed(boolean newArmed)
    {
        armed = newArmed;
        EmailSystem.sendMessage("Armed: " + newArmed);
    }

    @Override
    public void sensorDetection(Sensor s)
    {
        if(armed) 
        {
            alarm.ring();
            EmailSystem.sendMessage("Sensor detection for " + toString());
        } 
    }
}