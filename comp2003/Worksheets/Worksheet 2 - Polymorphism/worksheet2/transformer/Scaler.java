/*
 * NAME: ImageData
 * AUTHOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP2003
 * PURPOSE: Implement hooks to make Transformer.transform scale an image
 * CREATION: 25/03/2021 
 * LAST MODIFICATION: 25/03/2021
 */

public class Scaler extends Transformer {
    // HOOKS

    @Override
    protected int calcNewHeight(ImageData oldImage) {
        return oldImage.getHeight() / 2;
    }

    @Override
    protected int calcNewWidth(ImageData oldImage) {
        return oldImage.getWidth() / 2;
    }

    @Override
    protected int makeNewPixel(ImageData oldImage, int x, int y) {
        return oldImage.getPixel(x * 2, y * 2);
    }
}
