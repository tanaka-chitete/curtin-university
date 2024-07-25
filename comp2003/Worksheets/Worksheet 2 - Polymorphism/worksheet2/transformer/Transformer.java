/*
 * NAME: ImageData
 * AUTHOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP2003
 * PURPOSE: Implement overall algorithm for scale, rotate and invert transformations
 * CREATION: 25/03/2021 
 * LAST MODIFICATION: 25/03/2021
 */

public abstract class Transformer {
    // ABSTRACT METHODS

    protected abstract int calcNewHeight(ImageData oldImage);
    protected abstract int calcNewWidth(ImageData oldImage);
    protected abstract int makeNewPixel(ImageData oldImage, int x, int y);

    // OPERATORS

    ImageData transform(ImageData oldImage) {
        int newHeight = calcNewHeight(oldImage);
        int newWidth = calcNewWidth(oldImage);

        ImageData newImage = new ImageData(newWidth, newHeight);
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                newImage.setPixel(x, y, makeNewPixel(oldImage, x, y));
            }
        }

        return newImage;
    }
}
