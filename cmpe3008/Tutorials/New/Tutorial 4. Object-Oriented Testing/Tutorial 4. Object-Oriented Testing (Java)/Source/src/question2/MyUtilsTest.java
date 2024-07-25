import static org.mockito.Mockito.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MyUtilsTest 
{
    @Test 
    public void testMax()
    {
        // MyUtils myUtils = new MyUtils();
        MyUtils myUtils = mock(MyUtils.class);

        int result = myUtils.max(5, 1);
        System.out.println(result);

        /**
         * 2.2. Verification
         */

        // We can use this if we care about parameters:
        verify(myUtils).max(5,1); 

        // But sometimes it might not matter what the parameters were:
        verify(myUtils).max(anyInt(), anyInt()); 

        // We can also check how many times the method was called:
        verify(myUtils, times(1)).max(anyInt(), anyInt());

        /**
         * 2.3. Configuration
         */

        
    }

    @Test
    public void testPrintMax()
    {
        MyUtils myUtils = mock(MyUtils.class);
        printMax(myUtils, 5,1); // Call our production code
        verify(myUtils).max(5,1); // Verify the function was, indeed, called
    }

    public void printMax(MyUtils myUtils, int x, int y)
    {
        if (myUtils.max(x,y) == x)
        {
            System.out.println("x: " + x + " is max");
        }
        else
        {
            System.out.println("y: " + y + " is max");
        }
    }
}
