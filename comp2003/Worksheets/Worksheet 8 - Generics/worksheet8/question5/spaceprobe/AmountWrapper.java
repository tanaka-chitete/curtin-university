package edu.curtin.spaceprobe;

public class AmountWrapper<T> 
{
    private T amount;
    
    public AmountWrapper(T amount) {
        this.amount = amount;
    }

    public T getAmount() {
        return amount;
    }
}
