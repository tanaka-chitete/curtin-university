import java.util.*;

public class ImageTestHarness
{
    public static void main(String[] args)
    {
        try
        {
            Image[] image = new Image[4];

            // OBJECT INSTANTIATION
            image[0] = new Image();
            image[1] = new Image(Convolute.MATRIX_A);
            image[2] = new Image(image[1]);
            image[3] = image[1].clone();

            // PRINTING OF INSTANTIATED OBJECTS
            System.out.println("CONSTRUCTOR TESTS");
            for (int i = 0; i < image.length; i++)
            {
                System.out.println("Image[" + i + "]: " + image[i].toString() +
                                   '\n');
            }        

            // EQUALS METHOD TESTS
            System.out.println("EQUALS METHOD TESTS");
            System.out.println("Equals (object) (expected TRUE): " +
                                image[1].equals(image[3]));

            System.out.println("Equals (object) (expected TRUE): " + 
                                image[0].equals(image[3]));

            // MUTATORS (SETTERS) AND ACCESSORS (GETTERS) TESTS
            System.out.println("\nMUTATORS AND ACCESSORS");
            image[0].setOriginalImage(image[1].getOriginalImage());
            System.out.println(image[0].getOriginalImage() + " = " + 
                               image[1].getOriginalImage());

            image[0].setOriginalImage(image[2].getOriginalImage());
            System.out.println(image[0].getOriginalImage() + " = " +
                               image[2].getOriginalImage());

            image[0].setOriginalImage(image[1].getOriginalImage());
            System.out.println(image[0].getOriginalImage() + " = " +
                               image[1].getOriginalImage());

            // CONVOLUTION TESTS
            Image result = new Image(image[0].convolution(Kernel.HORIZONTAL));
            System.out.println(result.getOriginalImage());

        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
