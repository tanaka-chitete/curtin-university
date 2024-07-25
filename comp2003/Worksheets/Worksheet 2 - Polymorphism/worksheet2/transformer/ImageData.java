/*
 * NAME: ImageData
 * AUTHOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP2003
 * PURPOSE: Implement data structure to store pixels of an image 
 * CREATION: 25/03/2021 
 * LAST MODIFICATION: 25/03/2021
 */

public class ImageData {
    // PRIVATE CLASS FIELDS

    private int[][] image;

    // CONSTRUCTORS

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): width (int), height (int)
     * EXPORT(S): Address of new ImageData object
     * PURPOSE: Make new ImageData object in alternate state
     * CREATION: 25/03/2021
     * LAST MODIFICATION: 25/03/2021
     */

    public ImageData(int width, int height) {
        image = new int[height][width];
    }

    // SETTERS (MUTATORS)

    public void setPixel(int x, int y, int value) {
        image[y][x] = value;
    }

    // GETTERS (ACCESSORS)

    public int getPixel(int x, int y) {
        return image[y][x];
    }

    public int getHeight() {
        return image.length;
    }

    public int getWidth() {
        return image[0].length;
    }
}
