/**
 * Represents a single album entry.
 * 
 * @author Tanaka Chitete
 */

public class BasicEntry implements Entry
{
    // PRIVATE CLASS FIELDS

    private String imageName;
    private String caption;

    /*
    * ALTERNATE CONSTRUCTOR
    * IMPORT(S): name (String), caption (String)
    * EXPORT(S): Address of new BasicImage
    * PURPOSE: Create new BasicImage
    * CREATION: 15/03/2021
    * LAST MODIFICATION: 15/03/2021
    */

    public BasicEntry(String name, String caption) {
        this.imageName = name;
        this.caption = caption;
    }

    // SETTERS 

    @Override
    public void addToCaption(String additionalMetadata) {
        caption += additionalMetadata;
    }

    // GETTERS

    @Override
    public String getImageName() {
        return this.imageName;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }
}
