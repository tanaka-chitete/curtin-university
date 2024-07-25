/******************************************************************************
 * NAME: PDIMath                                                              *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1007                                                             *
 * PURPOSE: Implement various mathematical calculations                       *
 * CREATION: 05/05/2020                                                       *
 * LAST MODIFICATION: 05/05/2020                                              *
 ******************************************************************************/

/* This file comprises externally-obtained code. */

// Obtained from Chitete, T.,
// (Accessed 23/05/2020)
import java.util.*;

public class PDIMath
{
    // CLASS CONSTANTS
    
    public static final double SR_OF_12 = 3.464_101_615_137_755;

    public static final int PI_PRECISION_MIN = 5;
    public static final int PI_PRECISION_MAX = 20;

    public static final int E_PRECISION_MIN = 5;
    public static final int E_PRECISION_MAX = 20;
   
     // PUBLIC SUBMODULES
    
    /**************************************************************************
     * NAME: min                                                              *
     * IMPORT: a (int), b (int)                                               *
     * EXPORT: lowerVal (int)                                                 *
     * PURPOSE: Determine the lower value between ints a and b                *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static int min(int a, int b)
    {
        int lowerVal;

        lowerVal = a;
        if (b < a)
        {
            lowerVal = b;
        }
        return lowerVal;
    }

    /**************************************************************************
     * NAME: min                                                              *
     * IMPORT: a (long), b (long)                                             *
     * EXPORT: lowerVal (long)                                                *
     * PURPOSE: Determine the lower value between longs a and b               *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static long min(long a, long b)
    {
        long lowerVal;

        lowerVal = a;
        if (b < a)
        {
            lowerVal = b;
        }
        return lowerVal;
    }

    /**************************************************************************
     * NAME: min                                                              *
     * IMPORT: a (float), b (float)                                           *
     * EXPORT: lowerVal (float)                                               *
     * PURPOSE: Determine the lower value between floats a and b              *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static float min(float a, float b)
    {
        float lowerVal;

        lowerVal = a;
        if (b < a)
        {
            lowerVal = b;
        }
        return lowerVal;
    }

    /**************************************************************************
     * NAME: min                                                              *
     * IMPORT: a (double), b (double)                                         *
     * EXPORT: lowerVal (double)                                              *
     * PURPOSE: Determine the lower value between doubles a and b             *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double min(double a, double b)
    {
        double lowerVal;

        lowerVal = a;
        if (b < a)
        {
            lowerVal = b;
        }
        return lowerVal;
    }

    /**************************************************************************
     * NAME: max                                                              *
     * IMPORT: a (int), b (int)                                               *
     * EXPORT: higherVal (int)                                                *
     * PURPOSE: Determine the higher value between ints a and b               *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static int max(int a, int b)
    {
        int higherVal;

        higherVal = a;
        if (b > a)
        {
            higherVal = b;
        }
        return higherVal;
    }

    /**************************************************************************
     * NAME: max                                                              *
     * IMPORT: a (long), b (long)                                             *
     * EXPORT: higherVal (long)                                               *
     * PURPOSE: Determine the higher value between longs a and b              *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static long max(long a, long b)
    {
        long higherVal;

        higherVal = a;
        if (b > a)
        {
            higherVal = b;
        }
        return higherVal;
    }

    /**************************************************************************
     * NAME: max                                                              *
     * IMPORT: a (float), b (float)                                           *
     * EXPORT: higherVal (float)                                              *
     * PURPOSE: Determine the higher value between floats a and b             *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static float max(float a, float b)
    {
        float higherVal;

        higherVal = a;
        if (b > a)
        {
            higherVal = b;
        }
        return higherVal;
    }

    /**************************************************************************
     * NAME: max                                                              *
     * IMPORT: a (double), b (double)                                         *
     * EXPORT: higherVal (double)                                             *
     * PURPOSE: Determine the higher value between doubles a and b            *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double max(double a, double b)
    {
        double higherVal;

        higherVal = a;
        if (b > a)
        {
            higherVal = b;
        }
        return higherVal;
    }

    /**************************************************************************
     * NAME: abs                                                              *
     * IMPORT: a (int)                                                        *
     * EXPORT: absOfA (int)                                                   *
     * PURPOSE: Calculate the absolute value of int a                         *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static int abs(int a)
    {
        int absOfA;        

        absOfA = a;
        if (a < 0)
        {
            absOfA = a * -1;
        }
        return absOfA;
    }

    /**************************************************************************
     * NAME: abs                                                              *
     * IMPORT: a (long)                                                       *
     * EXPORT: absOfA (long)                                                  *
     * PURPOSE: Calculate the absolute value of long a                        *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static long abs(long a)
    {
        long absOfA;        

        absOfA = a;
        if (a < 0l)
        {
            absOfA = a * -1l;
        }
        return absOfA;
    }

    /**************************************************************************
     * NAME: abs                                                              *
     * IMPORT: a (float)                                                      *
     * EXPORT: absOfA (float)                                                 *
     * PURPOSE: Calculate the absolute value of float a                       *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static float abs(float a)
    {
        float absOfA;        

        absOfA = a;
        if (a < 0.0)
        {
            absOfA = a * -1.0f;
        }
        return absOfA;
    }

    /**************************************************************************
     * NAME: abs                                                              *
     * IMPORT: a (double)                                                     *
     * EXPORT: absOfA (double)                                                *
     * PURPOSE: Calculate the absolute value of double a                      *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double abs(double a)
    {
        double absOfA;        

        absOfA = a;
        if (a < 0.0)
        {
            absOfA = a * -1.0;
        }
        return absOfA;
    }

    /**************************************************************************
     * NAME: floor                                                            *
     * IMPORT: a (double)                                                     *
     * EXPORT: floorOfDoubleA (double)                                        *
     * PURPOSE: Calculate the floor of a                                      *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double floor(double a)
    {
        double floorOfDoubleA;
        int floorOfIntA;

        floorOfIntA = (int)a;
        floorOfDoubleA = (double)floorOfIntA;
        if (a < 0.0)
        {
            floorOfDoubleA -= 1.0;
        }
        return floorOfDoubleA;
    }

    /**************************************************************************
     * NAME: ceil                                                             *
     * IMPORT: a (double)                                                     *
     * EXPORT: ceilOfDoubleA (double)                                         *
     * PURPOSE: Calculate the ceiling of a                                    *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double ceil(double a)
    {
        double ceilOfDoubleA;
        int ceilOfIntA;

        ceilOfIntA = (int)a + 1;
        ceilOfDoubleA = (double)ceilOfIntA;
        return ceilOfDoubleA;
    }

    /**************************************************************************
     * NAME: pow                                                              *
     * IMPORT: base (double), exponent (int)                                  *
     * EXPORT: baseToExponent (double)                                        *
     * PURPOSE: Calculate the base raised to the power of the exponent        *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double pow(double base, int exponent)
    {
        double baseToExponent;

        if (base != 0.0)
        {
            if (exponent < 0 || exponent > 0)
            {
                if (exponent < 0)
                {
                    baseToExponent = 1.0 / base;
                    base = 1.0 / base;
                    exponent = exponent * -1;
                }
                else
                {
                    baseToExponent = base;
                }

                for (int i = 1; i < exponent; i++)
                {
                    baseToExponent = baseToExponent * base;
                }
            }
            else
            {
                baseToExponent = 1.0;
            }
        }
        else
        {
            throw new IllegalArgumentException("Math error: Base cannot be 0");
        }
        return baseToExponent;
    }

    /**************************************************************************
     * NAME: pi                                                               *
     * IMPORT: precision (int)                                                *
     * EXPORT: piToPrecision (double)                                         *
     * PURPOSE: Calculate approximation of pi to precision if it is greater   *
     *          than or equal to 5, FAIL if otherwise                         *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double pi(int precision)
    {
        double numer, denom, sum, piToPrecision;

        if ((PI_PRECISION_MIN <= precision) && (precision <= PI_PRECISION_MAX))
        {
            double[] terms = new double[precision];

            numer = -1.0;
            for (int i = 0; i < terms.length; i++)
            {
                numer = numer * -1.0;
                denom = calcDenomPi(i);
                terms[i] = numer / denom;
            }            

            sum = 0.0;
            for (int i = 0; i < terms.length; i++)
            {
                sum = sum + terms[i];
            }

            piToPrecision = sum * SR_OF_12;
        }
        else
        {
            throw new IllegalArgumentException("Argument error: Precision " +
                                               "must be between 5 and 20 " + 
                                               "inclusive");
        }
        return piToPrecision;
    }

    /**************************************************************************
     * NAME: e                                                                *
     * IMPORT: precision (int)                                                *
     * EXPORT: eToPrecision (double)                                          *
     * PURPOSE: Calculate approximation of e to precision if it is greater    *
     *          than or equal to 5, FAIL if otherwise                         *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    public static double e(int precision)
    {
        double sum, eToPrecision, factOfNRec;
        long factOfN;

        if ((E_PRECISION_MIN <= precision) && (precision <= E_PRECISION_MAX))
        {
            double[] terms = new double[precision];

            for (int i = 0; i < terms.length; i++)
            {
                factOfN = calcFactE(i);
                factOfNRec = 1.0 / (double)factOfN;
                terms[i] = factOfNRec;
            }

            sum = 0.0;
            for (int i = 0; i < terms.length; i++)
            {
                sum = sum + terms[i];
            }

            eToPrecision = sum;
        }
        else
        {
            throw new IllegalArgumentException("Precision must be between 5 " +
                                               "and 20 inclusive");
        }
        return eToPrecision;
    }

    // PRIVATE SUBMODULES

    /**************************************************************************
     * NAME: calcDenomPi                                                      *
     * IMPORT: n (int)                                                        *
     * EXPORT: denom (double)                                                 *
     * PURPOSE: Calculate denominator of term to be used in approximation of  *
     *          pi                                                            *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/
    
    private static double calcDenomPi(int n)
    {
        double term1, term2, denom;

        term1 = 2.0 * (double)n + 1.0;
        
        term2 = 1.0;
        for (int i = 0; i < n; i++)
        {
            term2 = term2 * 3.0;
        }

        denom = term1 * term2;
        return denom;
    }

    /**************************************************************************
     * NAME: calcFactE                                                        *
     * IMPORT: n (int)                                                        *
     * EXPORT: factOfN (long)                                                 *
     * PURPOSE: Calculate the factorial of n                                  *
     * CREATION: 05/05/2020                                                   *
     * LAST MODIFICATION: 05/05/2020                                          *
     **************************************************************************/

    private static long calcFactE(int n)
    {
        long factOfN;

        if (n == 0 || n == 1)
        {
            factOfN = 1;
        }
        else
        {
            factOfN = n * calcFactE(n - 1);
        }
        return factOfN;
    }
}
// End of code obtained from Chitete, T.
