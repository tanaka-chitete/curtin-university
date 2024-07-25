public class Wheel
{
    //Class constants.
    public static final String MAG = "MAGNESIUM";
    public static final String STEEL = "STEEL";

    //private class fields
    private String size, rimType, manufacturer;
    private double airPressure;

    /************************************************************
     * Default Constructor:
     * IMPORT: none
     * EXPORT: address of new Wheel object
     * ASSERTION: 185/55 R15 with an airpressure of 29, mag alloy
     *    rims and no manufacturer is a valid default state
     ************************************************************/
    public Wheel()
    {
        size = new String("185/55 R15");
        airPressure = 29.0;
        rimType = MAG;
        manufacturer = "";
    }

    /************************************************************
     * Alternate Constructor:
     * IMPORT: inSize (String), inPressure (real), inRim (String), inMake(String)
     * EXPORT: address of new Wheel object
     * ASSERTION: Creates the object if the imports are valid and FAILS otherwise
     ************************************************************/
    public Wheel(String inSize, double inPressure, String inRim, String inMake)
    {
        setSize(inSize);
        setAirPressure(inPressure);
        setRimType(inRim);
        setMake(inMake);
    }

    /************************************************************
     *Copy Constructor:
     *IMPORT: inWheel (Wheel)
     *EXPORT: address of new Wheel object
     *ASSERTION: Creates an object with an identical object state as the import.
     ************************************************************/
    public Wheel(Wheel inWheel)
    {
        size = inWheel.getSize();
        airPressure = inWheel.getAirPressure();
        rimType = inWheel.getRimType();
        manufacturer = inWheel.getMake();
    }

    //MUTATORS
    /************************************************************
     * SUBMODULE: setSize
     * IMPORT: inSize (String)
     * EXPORT: none
     * ASSERTION: sets the size to inSize.
     ************************************************************/
    public void setSize(String inSize)
    {
        size = inSize;
    }

    /********************************************************************
     * SUBMODULE: setAirPressure
     * IMPORT: inAirPressure (real)
     * EXPORT: none
     * ASSERTION: sets the current airPressure if valid and FAILS otherwise.
     *********************************************************************/
    public void setAirPressure(double inAirPressure)
    {
        if(validatePressure(inAirPressure))
        {
            airPressure = inAirPressure;
        }
        else
        {
            throw new IllegalArgumentException("Invalid Air Pressure");
        }
    }

    /********************************************************************
     *SUBMODULE: setRimType
     *IMPORT: inRimType (String)
     *EXPORT: none
     *ASSERTION: sets the rim type if valid and FAILS otherwise.
     *********************************************************************/
    public void setRimType(String inRimType)
    {
        if(validateRim(inRimType))
        {
            rimType = inRimType.toUpperCase();
        }
        else
        {
            throw new IllegalArgumentException("Invalid Rim Type");
        }
    }

    /********************************************************************
     * SUBMODULE: setMake
     * IMPORT: inManufacturer (String)
     * EXPORT: none
     * ASSERTION: sets the manufacturer.
     *********************************************************************/
    public void setMake(String inManufacturer)
    {
        manufacturer = inManufacturer;
    }

    //ACCESSORS
    public String getSize()
    {
        return size;
    }

    public double getAirPressure()
    {
        return airPressure;
    }

    public String getMake()
    {
        return manufacturer;
    }

    public String getRimType()
    {
        return rimType;
    }

    /********************************************************************
     * SUBMODULE: equals
     * IMPORT: inObj (Object)
     * EXPORT: same
     * ASSERTION: Two wheels are interchangeable if they have the same size and rimType.
     *********************************************************************/
    public boolean equals(Object inObj)
    {
        boolean same = false;
        if(inObj instanceof Wheel)
        {
            Wheel inWheel = (Wheel)inObj;
            same = size.equals(inWheel.getSize()) && 
                   rimType.equals(inWheel.getRimType());
        }
        return same;
    }

    public Wheel clone()
    {
        return new Wheel(this);
    }

    public String toString()
    {
        return ("size: " + size + " current air pressure: " + airPressure + 
                " rim matterial: " + rimType + " made by: " + manufacturer);
    }

    //PRIVATE SUBMODULES:
    /*************************************************************************
     * SUBMODULE: validatePressure
     * IMPORT: inPressure (real)
     * EXPORT: valid (boolean)
     * ASSERTION: pressure is between 0 and 42 (inclusive)
    ************************************************************************/
    private boolean validatePressure(double inPressure)
    {
        return ((inPressure >= 0.0) && (inPressure <= 42.0));
    }

    /*************************************************************************
     * SUBMODULE: validateRim
     * IMPORT: inRim (String)
     * EXPORT: valid (boolean)
     * ASSERTION: rim must be MAG or STEEL
    ************************************************************************/
    private boolean validateRim(String inRim)
    {
        String stripped = inRim.toUpperCase();
        return ((stripped.equals(MAG)) || (stripped.equals(STEEL)));
    }
}