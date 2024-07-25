package edu.curtin.spaceprobe;

public class FuelTank implements Resource<FuelAmount>
{
    private double oxygen = 100.0;
    private double hydrogen = 100.0;

    public void useUp(AmountWrapper<FuelAmount> amountWrapper)
    {
        FuelAmount fuelUsage = amountWrapper.getAmount();
        this.oxygen -= fuelUsage.getOxygen();
        this.hydrogen -= fuelUsage.getHydrogen();
    }

    @Override
    public AmountWrapper<FuelAmount> getRemaining()
    {
        return new AmountWrapper<FuelAmount>(new FuelAmount(hydrogen, oxygen));
    }

    @Override
    public long getTime(long elapsedTime)
    {
        return (long)Math.min(
            (double)elapsedTime / (100.0 - oxygen) * oxygen,
            (double)elapsedTime / (100.0 - hydrogen) * hydrogen);
    }
}
