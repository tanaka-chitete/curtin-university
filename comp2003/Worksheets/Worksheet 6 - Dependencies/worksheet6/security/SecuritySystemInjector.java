public class SecuritySystemInjector 
{
    public static void main(String[] args) 
    {
        ...
        Hardware hw = new Hardware(...);

        SensorBundle sens = hw.getSensors();
        MotionSensor mSens = sens.getMotionSensor();
        HeatSensor hSens = sens.getHeatSensor();
        Alarm alrm = hw.getAlarm();

        SecuritySystem ss = new SecuritySystem(mSens, hSens, alrm);
        ...
    }
}
