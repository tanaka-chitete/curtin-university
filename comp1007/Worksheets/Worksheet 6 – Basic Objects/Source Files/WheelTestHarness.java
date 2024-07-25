import java.util.*;

public class WheelTestHarness
{
    public static void main(String[] args)
    {
        try
        {
            Wheel[] wheel = new Wheel[4];

            //object creation
            wheel[0] = new Wheel();
            wheel[1] = new Wheel("190/22 R22", 21.87, Wheel.MAG, "Bridgestone");
            wheel[2] = new Wheel(wheel[1]);
            wheel[3] = wheel[1].clone();

            //print out created objects
            System.out.println("CONSTRUCTOR TESTS:");
            for(int i = 0; i < wheel.length; i++)
            {
                System.out.println("Wheel[" + i + "]: " + wheel[i].toString());
            }

            //equals method
            System.out.println("\nEQUALS METHOD TESTS:");
            System.out.println("Equals (object) expected TRUE: " + wheel[1].equals(wheel[3]));
            System.out.println("Equals (object) expected FALSE: " + wheel[0].equals(wheel[3]));
   
            //getters and setters
            System.out.println("\nGETTERS AND SETTERS:");
            wheel[0].setSize(wheel[1].getSize());
            System.out.println(wheel[0].getSize() + " = " + wheel[1].getSize());

            wheel[0].setMake(wheel[2].getMake());
            System.out.println(wheel[0].getMake() + " = " + wheel[2].getMake());

            wheel[0].setRimType(wheel[1].getRimType());
            System.out.println(wheel[0].getRimType() + " = " + wheel[1].getRimType());

            wheel[0].setAirPressure(wheel[1].getAirPressure());
            System.out.println(wheel[0].getAirPressure() + " = " + wheel[1].getAirPressure());
            
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
