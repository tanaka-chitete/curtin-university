// Based on code written by Chitete, T
// Accessed 10/08/2020

public class Student
{
    // CLASS CONSTANTS

    // null

    // PRIVATE CLASS FIELDS

    private int Id;
    private String name;

    // CONSTRUCTORS

    /**************************************************************************
     * DEFAULT CONSTRUCTOR                                                    * 
     * IMPORT: none                                                           *     
     * EXPORT: Address of new object                                          *
     * PURPOSE: Create object in default state with Id = "12345678" and name  *
     *          = "John Citizen"                                              * 
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 10/08/2020                                          *
     **************************************************************************/

    public Student()
    {
        Id = 12345678;
        name = "John Citizen";
    }

    /**************************************************************************
     * ALTERNATE CONSTRUCTOR                                                  *
     * IMPORT: inId (int), inName (String)                                    *
     * EXPORT: Address of new object                                          *
     * PURPOSE: Create object if imports are valid, FAIL if otherwise         *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 10/08/2020                                          *
     **************************************************************************/

    public Student(int inId, String inName)
    {
        setId(inId);
        setName(inName);
    }

    /**************************************************************************
     * COPY CONSTRUCTOR                                                       *
     * IMPORT: inStudent (Student)                                            *
     * EXPORT: Address of new date object                                     *
     * PURPOSE: Create object in state identical to import                    *
     * CREATION: 11/05/2020                                                   *
     * LAST MODIFICATION: 10/08/2020                                          *
     **************************************************************************/

    public Student(Student inStudent)
    {
        Id = inStudent.getId();
        name = inStudent.getName();
    }

    // ACCESSORS
    
    public int getId()
    {
        return Id;
    }

    public String getName()
    {
        return name;
    }
    
    // MUTATORS

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
        Id = inId;
    }

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
        name = inName;
    }
    
    // OPERATORS
    
    public String toFileString()
    {
        String stringOfObjectCSV = Id + "," + name;
        return stringOfObjectCSV;
    }

    public Student clone()
    {
        return new Student(this);
    }
    
    // PRIVATE SUBMODULES

    // null
}
