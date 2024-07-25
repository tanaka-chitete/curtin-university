package edu.curtin.spaceprobe;

import java.util.*;

public class SensorManager implements Resource<Set<Sensor>>
{
    private Set<Sensor> workingSensors;
    private int nSensors;

    public SensorManager(Set<Sensor> sensors)
    {
        workingSensors = new HashSet<>();
        workingSensors.addAll(sensors);
        nSensors = sensors.size();
    }

    @Override
    public void useUp(AmountWrapper<Set<Sensor>> amountWrapper)
    {
        for(Sensor sensor : amountWrapper.getAmount())
        {
            workingSensors.remove(sensor);
        }
    }

    @Override
    public AmountWrapper<Set<Sensor>> getRemaining()
    {
        return new AmountWrapper<Set<Sensor>>(Collections.unmodifiableSet(workingSensors));
    }

    @Override
    public long getTime(long elapsedTime)
    {
        double nWorking = (double)workingSensors.size();
        return (long)((double)elapsedTime / ((double)nSensors - nWorking) * nWorking);
    }
}
