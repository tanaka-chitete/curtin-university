import java.util.*;

public class StudentTestHarness
{
    public static void main(String[] args)
    {
        try
        {
            Student[] student = new Student[4];

            // OBJECTION INSTANTIATION
            student[0] = new Student();
            student[1] = new Student("Bertram Gilfoyle", 10100101, 74.0);
            student[2] = new Student(student[1]);
            student[3] = student[1].clone();

            // PRINTING OF INSTANTIATED OBJECTS
            System.out.println("CONSTRUCTOR TESTS");
            for (int i = 0; i < student.length; i++)
            {
                System.out.println("student[" + i + "]:\n" +
                                    student[i].toString());
            }

            // EQUALS METHOD TESTS
            System.out.println("\nEQUALS METHOD TESTS");
            System.out.println("Equals (object), expected: true, actual: " +
                                student[1].equals(student[3]));
    
            System.out.println("Equals (object), expected: false, actual: " +
                                student[0].equals(student[3]));

            // ACCESSORS AND MUTATORS
            System.out.println("\nACCESSORS AND MUTATORS");
            student[0].setName(student[1].getName());
            System.out.println(student[0].getName() + " = " + 
                               student[1].getName());

            student[0].setId(student[2].getId());
            System.out.println(student[0].getId() + " = " + 
                               student[2].getId());

            student[0].setMark(student[1].getMark());
            System.out.println(student[0].getMark() + " = " + 
                               student[1].getMark());
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
