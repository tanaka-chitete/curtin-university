public class Student
{
    // CLASS CONSTANTS

    public static final int ID_MIN = 10_000_000;
    public static final int ID_MAX = 99_999_999;

    public static final double MARK_MIN = 0.0;
    public static final double MARK_MAX = 100.0;
    public static final double MARK_TOL = 0.001;

    public static final int[] WHITESPACES = {9, 10, 11, 12, 13, 32};

    // PRIVATE CLASS FIELDS

    private String name;
    private int Id;
    private double mark;

    // CONSTRUCTORS

    /**************************************************************************
     * DEFAULT CONSTRUCTOR                                                    *       
     * IMPORT: none                                                           *     
     * EXPORT: Address of new object                                          *
     * PURPOSE: Create object in default state with name = "Tanaka Chitete",  * 
     *          Id = 20169321 and mark = 100.0                                *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    public Student()
    {
        name = "Tanaka Chitete";
        Id = 20169321;
        mark = 100.0;
    }

    /**************************************************************************
     * ALTERNATE CONSTRUCTOR                                                  *
     * IMPORT: inName (String), inId (int), inMark (double)                   *
     * EXPORT: Address of new object                                          *
     * PURPOSE: Create object if imports are valid, FAIL if otherwise         *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    public Student(String inName, int inId, double inMark)
    {
        setName(inName);
        setId(inId);
        setMark(inMark);
    }

    /**************************************************************************
     * COPY CONSTRUCTOR                                                       *
     * IMPORT: inStudent (Student)                                            *
     * EXPORT: Address of new date object                                     *
     * PURPOSE: Create object in state identical to import                    *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    public Student(Student inStudent)
    {
        name = inStudent.getName();
        Id = inStudent.getId();
        mark = inStudent.getMark();
    }

    // ACCESSORS

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return Id;
    }

    public double getMark()
    {
        return mark;
    }

    public String getGrade()
    {
        String grade;

        int markInt = (int)mark;
        markInt = markInt / 10;

        switch (markInt)
        {
            case 10: case 9: case 8:
                grade = "High Distinction";
                break;
            case 7:
                grade = "Distinction";
                break;
            case 6:
                grade = "Credit";
                break;
            case 5:
                grade = "Pass";
                break;
            default:
                grade = "Fail";
                break;
        }
        return grade;
    }               

    // MUTATORS

    /**************************************************************************
     * NAME: setName                                                          *
     * IMPORT: inName (String)                                                *
     * EXPORT: none                                                           *
     * PURPOSE: Set name to inName if valid, FAIL if otherwise                *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/ 
    
    public void setName(String inName)
    {
        if (validateInName(inName))
        {
            name = inName;
        }
        else
        {
            throw new IllegalArgumentException("Name parameter is invalid");
        }
    }

    /**************************************************************************
     * NAME: setId                                                            *
     * IMPORT: inId (int)                                                     *
     * EXPORT: none                                                           *
     * PURPOSE: Set Id to inId if valid, FAIL if otherwise                    *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/ 
    
    public void setId(int inId)
    {
        if (validateInId(inId))
        {
            Id = inId;
        }
        else
        {
            throw new IllegalArgumentException("ID parameter is invalid");
        }
    }

    /**************************************************************************
     * NAME: setMark                                                          *
     * IMPORT: inMark (double)                                                *
     * EXPORT: none                                                           *
     * PURPOSE: Set mark to inMark if valid, FAIL if otherwise                *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/ 
    
    public void setMark(double inMark)
    {
        if (validateInMark(inMark))
        {
            mark = inMark;
        }
        else
        {
            throw new IllegalArgumentException("Mark parameter is invalid");
        }
    }

    // OPERATORS

    public String toString()
    {
        String stringOfObject = "Name: " + name + ", ID: " + Id + ", Mark: " + 
                                 mark + ", Grade: " + getGrade();
        return stringOfObject; 
    }

    public String toFileString()
    {
        String stringOfObjectCSV = name + "," + Id + "," + mark;
        return stringOfObjectCSV;
    }

    public Student clone()
    {
        return new Student(this);
    }

    /**************************************************************************
     * NAME: equals                                                           *
     * IMPORT: inObject (Object)                                              *
     * EXPORT: sameAll (boolean)                                              *
     * PURPOSE: Validate whether or not Object and inObject share the same    *
     *          values for name and Id class fields                           *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    public boolean equals(Object inObject)
    {
        boolean sameAll = false;
        if (inObject instanceof Student)
        {
            Student inStudent = (Student)inObject;
            boolean sameName = name.equals(inStudent.getName());
            boolean sameId = Id == inStudent.getId();
            sameAll = sameName && sameId;
        }
        return sameAll;
    }

    // PRIVATE SUBMODULES

    /**************************************************************************
     * NAME: validateInName                                                   *
     * IMPORT: inName (String)                                                *
     * EXPORT: validInName (boolean)                                          *
     * PURPOSE: Validate whether or not inName is blank or null               *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    private boolean validateInName(String inName)
    {
        int i, j;
        
        boolean validInName = false;
        if (!(inName == null))
        {
            i = 0;
            while (!validInName && i < inName.length())
            {
                j = 0;
                while (!validInName && j < WHITESPACES.length)
                {
                    if ((int)inName.charAt(i) != WHITESPACES[j])
                    {
                        validInName = true;
                    }
                    j++;
                }
                i++;
            }
        }
        return validInName;
    }

    /**************************************************************************
     * NAME: validateInId                                                     *
     * IMPORT: inId (int)                                                     *
     * EXPORT: validInId (boolean)                                            *
     * PURPOSE: Validate whether or not inId is between 10,000,000 and        *
     *          99,999,999 inclusive                                          *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/
    
    private boolean validateInId(int inId)
    {
        boolean validInId = false;
        if (ID_MIN <= inId && inId <= ID_MAX)
        {
            validInId = true;
        }
        return validInId;
    }    
 
    /**************************************************************************
     * NAME: validInMark                                                      *
     * IMPORT: inMark (double)                                                *
     * EXPORT: validInMark (boolean)                                          *
     * PURPOSE: Validate whether or not inMark is between 0.0 and 100.0       *
     *          inclusive                                                     *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    public boolean validateInMark(double inMark)
    {
        boolean validInMark = false;
        if ((MARK_MIN - MARK_TOL < inMark) && (inMark < MARK_MAX + MARK_TOL))
        {
            validInMark = true;
        }
        return validInMark;
    }   
}
