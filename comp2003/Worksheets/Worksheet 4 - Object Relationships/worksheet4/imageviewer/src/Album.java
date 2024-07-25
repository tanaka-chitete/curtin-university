import java.util.*;

/**
 * Represents an album of images.
 */
public class Album 
{
    // PRIVATE CLASS FIELDS

    private ArrayList<Entry> imgs;
    private int currImgIdx;

   /*
    * DEFAULT CONSTRUCTOR
    * IMPORT(S): None
    * EXPORT(S): Address of new Album
    * PURPOSE: Create new Album
    * CREATION: 15/03/2021
    * LAST MODIFICATION: 15/03/2021
    */

    public Album() 
    {
        this.imgs = new ArrayList<Entry>();
        this.currImgIdx = 0;
    }

    // SETTERS

    public void add(Entry image) {
        imgs.add(image);
    }

    public void add(String imageFilename, String imageCaption) 
    {
        Entry newImage = new BasicEntry(imageFilename, imageCaption);

        imgs.add(newImage);
    }

    // GETTERS

    public Entry next() {
        Entry nextImg;

        try {
            nextImg = imgs.get(currImgIdx);
            currImgIdx++;
        }
        catch (IndexOutOfBoundsException e) {
            currImgIdx--;
            nextImg = imgs.get(currImgIdx);
        }

        return nextImg;
    }

    public Entry previous() {
        Entry prevImg;

        try {
            currImgIdx--;
            prevImg = imgs.get(currImgIdx);
        }
        catch (IndexOutOfBoundsException e) {
            currImgIdx++;
            prevImg = imgs.get(currImgIdx);
        }

        return prevImg;
    }
}
