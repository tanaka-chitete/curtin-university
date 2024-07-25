package edu.curtin.spaceprobe;

public interface Resource<T>
{
    void useUp(AmountWrapper<T> amountWrapper);
    AmountWrapper<T> getRemaining();
    long getTime(long elapsedTime);
}
