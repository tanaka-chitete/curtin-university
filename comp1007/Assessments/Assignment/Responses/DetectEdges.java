/******************************************************************************
 * NAME: DetectEdges                                                          *
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1007                                                             * 
 * PURPOSE: Detect vertical and horizontal lines in CSV and PNG images        * 
 * CREATION: 27/05/2020                                                       *
 * LAST MODIFICATION: 31/05/2020                                              *
 ******************************************************************************/

import java.util.*;

public class DetectEdges
{
    public static final int OPTION_0 = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    public static final int OPTION_4 = 4;
    public static final int OPTION_5 = 5;
    public static final int OPTION_6 = 6;
    public static final int OPTION_7 = 7;

    public static void main(String[] args)
    {
        int sel;
        String prompt;

        int[][] imgArr = null;
        int[][] kerArr = null;
        int[][] conArr = null;

        do
        {
            UserInterface.printMainMenu();
            prompt = "Selection: ";
            sel = UserInterface.userInput(OPTION_0, OPTION_7, prompt);
            System.out.println();

            switch (sel)
            {
                case 1:
                    imgArr = Menu.importImage();
                    break;
                case 2:
                    kerArr = Menu.importKernel();
                    break;
                case 3:
                    conArr = Menu.convoluteImage(imgArr, kerArr); 
                    break;
                case 4:
                    imgArr = Menu.smoothenImageOrConvolute(imgArr);
                    break;
                case 5:
                    conArr = Menu.smoothenImageOrConvolute(conArr);
                    break;
                case 6:
                    Menu.exportImageOrConvolute(imgArr);
                    break;
                case 7:
                    Menu.exportImageOrConvolute(conArr);
                    break;
                default:
                    UserInterface.printMessage("Program Ended\n");
                    break;
            }
        } 
        while (sel != OPTION_0);
    }
}
