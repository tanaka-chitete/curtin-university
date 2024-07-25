/******************************************************************************
 * NAME: ReadSortAndWrite                                                     * 
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1002                                                             * 
 * PURPOSE: Perform various sorting algorithms on Students.csv                *
 * CREATION: 19/05/2020                                                       *  
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

import java.util.*;

// Based on code written by Chitete, T
// Accessed 10/08/2020

public class ReadSortAndWrite
{
    public static final int OPTION_0 = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    
    public static final int ASCII_OF_UC_A = 65;
    public static final int ASCII_OF_UC_Z = 90;
    
    public static final int UC_OR_LC_KEY = 32;

    public static final String NAME_OF_FILE = "Students.csv";

    public static void main(String[] args)
    {
        String prompt;
        int menuSel;

        // Reads CSV file rows with name, Id and mark columns into temp
        String[][] temp = new String[7000][2];
        temp = FileIO.readCSV(NAME_OF_FILE);

        // Copies rows of temp to students 
        Student[] students = new Student[7000];
        students = tempToStudents(temp, students);

        do
        {
            System.out.println("Main Menu\n\n" +

                               "1. Sort\n" +
                               "0. End Program\n");

            prompt = "Selection: ";
            menuSel = UserInterface.userInput(OPTION_0, OPTION_2, prompt);
            System.out.println();

            switch (menuSel)
            {
                // Executes if input specifies 'Sort'
                case 1:
                    sort(students);
                    break;
                // Executes if input specifies 'End program'
                case 0:
                    System.out.println("End Program\n");
                    break;
                // Executes if input specifies none of the previous options
                default:
                    System.out.println("Please enter either 1 or 0\n");
            }
        }
        while (menuSel != OPTION_0);
    }

    /**************************************************************************
     * NAME: tempToStudents                                                   *
     * IMPORT: temp (String[][]), students (Student[])                        *
     * EXPORT: students (Student[])                                           *
     * PURPOSE: Copy rows of temp to students                                 *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static Student[] tempToStudents(String[][] temp, Student[] students)
    {
        String Id, name;

        for (int i = 0; i < temp.length; i++)
        {
            Student student = new Student();

            Id = temp[i][0];
            name = temp[i][1];

            student.setId(Integer.parseInt(Id));
            student.setName(name);

            students[i] = student;
        }
        return students;
    }

    /**************************************************************************
     * NAME: sort                                                             *
     * IMPORT: students (Student[])                                           *
     * EXPORT: none                                                           *
     * PURPOSE: Executes 'Sort' and its related processes                     *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 10/08/2020                                          *
     **************************************************************************/

    public static void sort(Student[] students)
    {
        String prompt;
        int sortingAlgorithm;
        String sortedFileName = "ERROR.csv";

        System.out.println("Sorting Algorithm Selection\n\n" +

                           "1. Bubble Sort\n" +
                           "2. Insertion Sort\n" +
                           "3. Selection Sort\n");

        prompt = "Selection: ";
        sortingAlgorithm = UserInterface.userInput(OPTION_1, OPTION_3, prompt);
        System.out.println();

        switch (sortingAlgorithm)
        {
            // Executes if input specifies 'Bubble Sort'
            case 1:
                SortsStudent.bubbleSort(students);
                // Writes sorted students to "Students_bS" CSV
                sortedFileName = "Students_bS.csv";
                FileIO.writeCSV(sortedFileName, students);
                break;
            // Executes if input specifies 'Insertion Sort'
            case 2:
                SortsStudent.insertionSort(students);
                // Writes sorted students to "Students_iS" CSV                    
                sortedFileName = "Students_iS.csv";
                FileIO.writeCSV(sortedFileName, students);
                break;
            // Executes if input specifies 'Selection Sort'
            case 3:
                SortsStudent.selectionSort(students);
                // Writes sorted students to "Students_sS" CSV
                sortedFileName = "Students_sS.csv";
                FileIO.writeCSV(sortedFileName, students);
                break;
            // Executes if input specifies none of the previous options
            default:
                System.out.println("Input was neither 1, 2 or 3");
                break;
        }

        if (1 <= sortingAlgorithm && sortingAlgorithm <= 3)
            System.out.println("Sorted file saved as " + sortedFileName + "\n"); 
    }
}
