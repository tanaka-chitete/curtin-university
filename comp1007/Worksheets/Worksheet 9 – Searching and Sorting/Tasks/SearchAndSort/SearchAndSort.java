/******************************************************************************
 * NAME: SearchAndSort                                                        * 
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1007                                                             * 
 * PURPOSE: Perform various sort and search algorithms on RandomNames7000.csv *
 * CREATION: 19/05/2020                                                       *  
 * LAST MODIFICATION: 19/05/2020                                              *
 ******************************************************************************/

import java.util.*;

public class SearchAndSort
{
    public static final int OPTION_0 = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    
    public static final int ASCII_OF_UC_A = 65;
    public static final int ASCII_OF_UC_Z = 90;
    
    public static final int UC_OR_LC_KEY = 32;

    public static final String NAME_OF_FILE = "RandomNames7000.csv";

    public static void main(String[] args)
    {
        boolean sorted;
        String prompt;
        int menuSel;

        // Reads CSV file rows with name, Id and mark columns into temp
        String[][] temp = new String[7000][3];
        temp = FileIO.readCSV(NAME_OF_FILE);

        // Copies rows of temp to students 
        Student[] students = new Student[7000];
        students = tempToStudents(temp, students);

        sorted = false;
        do
        {
            System.out.println("Main Menu\n\n" +

                               "1. Sort\n" +
                               "2. Search\n" +
                               "0. End Program\n");

            prompt = "Selection: ";
            menuSel = UserInterface.userInput(OPTION_0, OPTION_2, prompt);
            System.out.println();

            switch (menuSel)
            {
                // Executes if input specifies 'Sort'
                case 1:
                    sorted = sort(students, sorted);
                    break;
                // Executes if input specifies 'Search'
                case 2:
                    search(students, sorted);
                    break;
                // Executes if input specifies 'End program'
                case 0:
                    System.out.println("End Program\n");
                    break;
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
        String name, Id, mark;

        for (int i = 0; i < temp.length; i++)
        {
            Student student = new Student();

            name = temp[i][0];
            Id = temp[i][1];
            mark = temp[i][2];

            student.setName(name);
            student.setId(Integer.parseInt(Id));
            student.setMark(Double.parseDouble(mark));

            students[i] = student;
        }
        return students;
    }

    /**************************************************************************
     * NAME: sort                                                             *
     * IMPORT: students (Student[]), sorted (boolean)                         *
     * EXPORT: sorted (boolean)                                               *
     * PURPOSE: Executes 'Sort' and its related processes                     *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static boolean sort(Student[] students, boolean sorted)
    {
        String prompt;
        int sortingAlgorithm;
        long start, finish;
        long timeTaken;

        // Executes if students was sorted
        if (!sorted)
        {
            System.out.println("Sorting Algorithm Selection\n\n" +

                               "1. Bubble Sort\n" +
                               "2. Insertion Sort\n" +
                               "3. Selection Sort\n");

            prompt = "Selection: ";
            sortingAlgorithm = UserInterface.userInput(OPTION_1, 
                                                       OPTION_3, 
                                                       prompt);
            System.out.println();

            // Starts timing time taken to sort students
            start = System.nanoTime();
            switch (sortingAlgorithm)
            {
                // Executes if input specifies 'Bubble Sort'
                case 1:
                    bubbleSort(students);
                    // Writes sorted students to "RandomNames7000_bS" CSV
                    // FileIO.writeCSV("RandomNames7000_bS.csv", students);
                    break;
                // Executes if input specifies 'Insertion Sort'
                case 2:
                    insertionSort(students);
                    // Writes sorted students to "RandomNames7000_iS" CSV
                    // FileIO.writeCSV("RandomNames7000_iS.csv", students);
                    break;
                // Executes if input specifies 'Selection Sort'
                case 3:
                    selectionSort(students);
                    // Writes sorted students to "RandomNames7000_sS" CSV
                    // FileIO.writeCSV("RandomNames7000_sS.csv", students);
                    break;
            }
            // Stops timing time taken to sort students
            finish = System.nanoTime();
            sorted = true;

            // Calculates time taken to sort students
            timeTaken = (long)((double)(finish - start) / 1_000_000.0);
        
            System.out.println("Sorting time: " + timeTaken + " milliseconds\n");
        }
        else
        {
            UserInterface.printError("Students have already been sorted\n" +
                                     "Restart program to execute Sort again\n");
        }
        return sorted;
    }

    /**************************************************************************
     * NAME: bubbleSort                                                       *
     * IMPORT: students (Student[])                                           *
     * EXPORT: students (Student[])                                           *
     * PURPOSE: Sorts students by Id using optimised Bubble Sort              *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static Student[] bubbleSort(Student[] students)
    {
        int pass;
        boolean sorted;
        Student temp = new Student();

        pass = 0;
        do
        {
            sorted = true;
            for (int i = 0; i < (students.length - 1) - pass; i++)
            {
                if (students[i].getId() > students[i + 1].getId())
                {
                    temp = students[i];
                    students[i] = students[i + 1];
                    students[i + 1] = temp;
                    sorted = false;
                }
            }
            pass++;
        }
        while (!sorted);
        return students;
    }

    /**************************************************************************
     * NAME: insertionSort                                                    *
     * IMPORT: students (Student[])                                           * 
     * EXPORT: students (Student[])                                           *
     * PURPOSE: Sorts students by Id using Insertion Sort                     *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static Student[] insertionSort(Student[] students)
    {
        int i;
        Student key = new Student();

        for (int n = 1; n < students.length; n++)
        {
            key = students[n];
            i = n - 1;
            while (i >= 0 && (students[i].getId() > key.getId()))
            {
                students[i + 1] = students[i];
                i--;
            }
            students[i + 1] = key;
        }
        return students;
    }

    /**************************************************************************
     * NAME: selectionSort                                                    *
     * IMPORT: students (Student[])                                           *
     * EXPORT: students (Student[])                                           *
     * PURPOSE: Sorts students by Id using Selection Sort                     *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static Student[] selectionSort(Student[] students)
    {
        int idxOfMinId;
        Student temp = new Student();

        for (int n = 0; n < students.length - 1; n++)
        {
            idxOfMinId = n;
            for (int j = n + 1; j < students.length - 1; j++)
            {
                if (students[j].getId() < students[idxOfMinId].getId())
                {
                    idxOfMinId = j;
                }
            }
            temp = students[idxOfMinId];
            students[idxOfMinId] = students[n];
            students[n] = temp;
        }
        return students;
    }

    /**************************************************************************
     * NAME: search                                                           *
     * IMPORT: students (Students[]), sorted (boolean)                        *
     * EXPORT: none                                                           *
     * PURPOSE: Executes 'Search' and its related processes                   *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static void search(Student[] students, boolean sorted)
    {
        String prompt;
        int searchingAlgorithm;
        String nameOfStudent;
        boolean found;
        long start, finish;
        int idxOfMatchStudent; 
        long timeTaken; 
        int idxOfMathName, IdOfStudent;

        System.out.println("Searching Algorithm Selection\n\n" + 

                           "1. Linear Search\n" +
                           "2. Binary Search\n");

        prompt = "Selection: ";
        searchingAlgorithm = UserInterface.userInput(OPTION_1, 
                                                     OPTION_2, 
                                                     prompt);
        System.out.println();

        found = false;
        idxOfMatchStudent = -1;
        // Executes if input specifies 'Linear Search'
        if (searchingAlgorithm == OPTION_1)
        {
            prompt = "Name of student to search for: ";
            nameOfStudent = UserInterface.userInput(prompt);
            // Converts nameOfStudent to its lowercase equivalent
            nameOfStudent = toLowerString(nameOfStudent);
            System.out.println();

            // Gets position of nameOfStudent in students from Linear Search
            start = System.nanoTime();
            idxOfMatchStudent = linearSearch(students, nameOfStudent);
            finish = System.nanoTime();

            // Calculates time taken to search students for nameOfStudent
            timeTaken = (long)((double)(finish - start) / 1_000_000.0);
            System.out.println("Searching time: " + timeTaken + " milliseconds\n");
            if (idxOfMatchStudent >= 0)
            {
                // Prints position in students nameOfStudent was found at
                System.out.println("Position found at: " + 
                                    (idxOfMatchStudent + 1) + "\n");

                // Prints nameOfStudent's information
                System.out.println(students[idxOfMatchStudent].toString() + 
                "\n");
            }
            else
            {
                UserInterface.printError("Student not found\n");
            }
        }
        else
        {
            // Executes if students has not already been sorted
            if (!sorted)
            {
                UserInterface.printError("Students must be sorted first\n");
            }
            else
            {
                prompt = "Name of student to search for: ";
                nameOfStudent = UserInterface.userInput(prompt);
                // Converts nameOfStudent to its lowercase equivalent
                nameOfStudent = toLowerString(nameOfStudent);
                System.out.println();

                // Gets position of nameOfStudent in students from Linear Search
                idxOfMatchStudent = linearSearch(students, nameOfStudent);
                // Gets Id of nameOfStudent in students using idxOfMatchStudent
                if (idxOfMatchStudent >= 0)
                {
                    IdOfStudent = students[idxOfMatchStudent].getId();

                    // Gets position of IdOfStudent in students from Binary Search
                    start = System.nanoTime();
                    idxOfMatchStudent = binarySearch(students, IdOfStudent);
                    finish = System.nanoTime();
            
                    // Calculates time taken to search students for idOfStudent
                    timeTaken = (long)((double)(finish - start) / 1_000_000.0);
                    System.out.println("Searching time: " + timeTaken + " milliseconds\n");
                
                    // Prints position in students nameOfStudent was found at
                    System.out.println("Position found at: " + 
                                        (idxOfMatchStudent + 1) + "\n");

                    // Prints nameOfStudent's information
                    System.out.println(students[idxOfMatchStudent].toString() + 
                                       "\n");
                }
                else
                {
                    UserInterface.printError("Student not found\n");
                }
            }
        }
    }

    /**************************************************************************
     * NAME: linearSearch                                                     *
     * IMPORT: students (Student[]), nameOfStudent (String)                   *
     * EXPORT: idxOfMatchStudent (int)                                        *
     * PURPOSE: Search students for nameOfStudent using Linear Search         *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/    

    public static int linearSearch(Student[] students, String nameOfStudent)
    {
        int i, idxOfMatchStudent;
        String nameOfCurrentStudent;

        i = 0;
        idxOfMatchStudent = -1;
        nameOfStudent = toLowerString(nameOfStudent);
        while ((i < students.length) && (idxOfMatchStudent == -1))
        {
            nameOfCurrentStudent = toLowerString(students[i].getName());
            if (nameOfStudent.equals(nameOfCurrentStudent))
            {
                idxOfMatchStudent = i;
            }
            i++;
        }
        return idxOfMatchStudent;
    }

    /**************************************************************************
     * NAME: binarySearch                                                     *
     * IMPORT: studentsSorted (Student[]), IdOfStudent (int)                  *
     * EXPORT: idxOfMatchStudent (int)                                        *
     * PURPOSE: Search studentsSorted for IdOfStudent using Binary Search     *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static int binarySearch(Student[] studentsSorted, int IdOfStudent)
    {
        int lB, uB, idxOfMatchStudent;
        boolean found;
        int idxToCheck;

        lB = 0;
        uB = studentsSorted.length;
        idxOfMatchStudent = -1;

        found = false;
        while (!found && (lB <= uB))
        {
            idxToCheck = (lB + uB) / 2;
            if (studentsSorted[idxToCheck].getId() < IdOfStudent)
            {
                lB = idxToCheck + 1;
            }
            else if (studentsSorted[idxToCheck].getId() > IdOfStudent)
            {
                uB = idxToCheck - 1;
            }
            else
            {
                idxOfMatchStudent = idxToCheck;
                found = true;
            }
        }
        return idxOfMatchStudent;
    }

    /**************************************************************************
     * NAME: toLowerString                                                    *
     * IMPORT: string (String)                                                *
     * EXPORT: stringLC (String)                                              *
     * PURPOSE: Convert a string into its lowercase equivalent                *
     * CREATION: 19/05/2020                                                   *
     * LAST MODIFICATION: 19/05/2020                                          *
     **************************************************************************/

    public static String toLowerString(String string)
    {
        String stringLC;
        char character;

        stringLC = "";
        for (int i = 0; i < string.length(); i++)
        {
            character = string.charAt(i);
            if ((ASCII_OF_UC_A <= character) && (character <= ASCII_OF_UC_Z))
            {
                character = (char)((int)character + UC_OR_LC_KEY);
            }
            stringLC += character;
        }
        return stringLC;
    }
}
