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
        MyUtils myUtils = new MyUtils();

        assertEquals("20 > 10", 20, myUtils.max(10, 20));
        assertEquals("10 = 10", 10, myUtils.max(10, 10));
        assertEquals("10 > 5", 10, myUtils.max(5, 10));
    }
}
