/******************************************************
 * FILE: Kernel.java                                  *
 * PURPOSE: Provides Kernels for Convolution Operation*
 * AUTHOR: Curtin University                          *
 * MODIFIED BY: ___________                           *
 * DATE CREATED: 24/03/2020                           *
 * LAST MOD: 26/03/2020, 4:37PM                       *
 ******************************************************/

public class Kernel
{
        /*
         * HORIZONTAL - A kernel that detects horizontal lines.
         */
    public static final int[][] HORIZONTAL =
        {
                { 1,  1,  1},
                { 0,  0,  0},
                {-1, -1, -1}
        }; 
        /*
         * VERTICAL - A kernel that detects vertical lines.
         */
    public static final int[][] VERTICAL =
        {
                { 1,  0, -1},
                { 1,  0, -1},
                { 1,  0, -1}
        }; 

}
