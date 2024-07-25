public class Image
{
    // CLASS CONSTANTS
    public static final int IMAGE_ROW_COUNT_MIN = 3;
    public static final int IMAGE_COL_COUNT_MIN = 3;

    // PRIVATE CLASS FIELDS
    private int[][] originalImage = new int[][] {{0}};

    // CONSTRUCTORS
    
    /**************************************************************************
     * DEFAULT CONSTRUCTOR                                                    * 
     * IMPORT: none                                                           *
     * EXPORT: Address of new image object                                    *
     * ASSERTION: 6 rows by 6 columns with all elements in the first three    *
     *            columns assigned the value 10 and the remainder 1 is a      *
     *            valid default state                                         *
     **************************************************************************/

    public Image()
    {
        int[][] originalImage =
        {
            {10, 10, 10, 1, 1, 1},
            {10, 10, 10, 1, 1, 1},
            {10, 10, 10, 1, 1, 1},
            {10, 10, 10, 1, 1, 1},
            {10, 10, 10, 1, 1, 1},
            {10, 10, 10, 1, 1, 1}
        };
    }

    /**************************************************************************
     * ALTERNATE CONSTRUCTOR                                                  *
     * IMPORT: inOriginalImage (int[][])                                      *
     * EXPORT: Address of new Image object                                    *
     * ASSERTION: Object will be created if imports are valid, FAIL if        *
     *            otherwise                                                   *
     **************************************************************************/

    public Image(int[][] inOriginalImage)
    {
        setOriginalImage(inOriginalImage);
    }

    /**************************************************************************
     * COPY CONSTRUCTOR                                                       *
     * IMPORT: inImage (Image)                                                *
     * EXPORT: Address of new Image object                                    *
     * ASSERTION: Object will be created if imports are valid, FAIL if        *
     *            otherwise                                                   *
     **************************************************************************/

    public Image(Image inImage)
    {
        int[][] originalImage = new int[inImage.getRowCount()]
                                       [inImage.getColCount()];
        
        for (int i = 0; i < inImage.getRowCount(); i++)
        {
            for (int j = 0; j < inImage.getColCount(); j++)
            {
                originalImage[i][j] = inImage.getPixel(i,j);
            }
        }
    }

    // MUTATORS (SETTERS)

    /**************************************************************************
     * SUBMODULE: setOriginalImage                                            *
     * IMPORT: inOriginalImage (int[][])                                      *
     * EXPORT: none                                                           *
     * ASSERTION: originalImage and inOriginalImage will be called to         * 
     *            deepCopy if inOriginalImage is valid, FAIL if otherwise     *
     **************************************************************************/

    public void setOriginalImage(int[][] inOriginalImage)
    {
        // Calls originalImage and inOriginalImage to deepcopy if valid
        if (validateInOriginalImage(inOriginalImage))
        {
            int[][] originalImage = new int[inOriginalImage.length]
                                           [inOriginalImage[0].length];

            deepCopy(originalImage, inOriginalImage);
        }
        // Fails if otherwise
        else
        {
            throw new IllegalArgumentException("Original Image input is " +
                                               "invalid");
        }
    } 

    // ACCESSORS (GETTERS)

    public int[][] getOriginalImage()
    {
        return originalImage;   
    }

    public int getPixel(int row, int column)
    {
        return originalImage[row][column];
    }

    public int getRowCount()
    {
        return originalImage.length;
    }

    public int getColCount()
    {
        return originalImage[0].length;
    }

    /**************************************************************************
     * SUBMODULE: equals                                                      *
     * IMPORT: inObject (Object)                                              *
     * EXPORT: same (Boolean)                                                 *
     * ASSERTION: Two original images are the same if they share all of the   *
     *            same elements                                               *
     **************************************************************************/

    public boolean equals(Object inObject)
    {
        boolean same;
        int inRowCount, inColCount, rowCount, colCount;
        int i, j;

        same = true;
        if (inObject instanceof Image)
        {
            Image inOriginalImage = (Image)inObject;

            inRowCount = inOriginalImage.getRowCount();
            inColCount = inOriginalImage.getColCount();

            rowCount = originalImage.length;
            colCount = originalImage[0].length;

            if ((inRowCount == rowCount) && (inColCount == colCount))
            {
                i = 0;
                while (same && (i < rowCount))
                {
                    j = 0;
                    while (same && (j < colCount))
                    {
                        if (inOriginalImage.getPixel(i, j) != 
                            originalImage[i][j])
                        {
                            same = false;
                        }
                        j++;
                    }
                    i++;
                }
            }
        }
        return same;
    }

    public Image clone()
    {
        return new Image(this);
    }

    public String toString()
    {
        int rowCount, colCount;
        String str;
    
        rowCount = originalImage.length;
        colCount = originalImage[0].length;

        str = "";
        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < colCount; j++)
            {
                str = str + originalImage[i][j] + "  ";
            }
            System.out.println();
        }
        return str;
    }

    public int[][] convolution(int[][] inKernel)
    {
        int resRowCount, resColCount, sum;

        resRowCount = originalImage.length - inKernel.length + 1;
        resColCount = originalImage[0].length - inKernel[0].length + 1;

        int[][] result = new int[resRowCount][resColCount];
        
        if (validateInKernel(inKernel))
        {
            sum = 0;
            for (int i = 0; i < result.length; i++)
            {
                for (int j = 0; j < result[0].length; j++)
                {
                    for (int k = 0; k < inKernel.length; k++)
                    {
                        for (int l = 0; l < inKernel[0].length; l++)
                        {
                            sum = sum + (originalImage[i + k][j + l] *
                                         inKernel[k][l]);
                        }
                    }
                    result[i][j] = sum;
                    sum = 0;
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Original image dimensions " + 
                                               "must be greater than or " + 
                                               "equal to kernel dimensions");
        }
        return result;
    }

    // PRIVATE SUBMODULES
    
    /**************************************************************************
     * SUBMODULE: validateInOriginalImage                                     *
     * IMPORT: inOriginalImage (int[][])                                      *
     * EXPORT: validInOriginalImage (Boolean)                                 *
     * ASSERTION: inOriginalImage will be validated as having row and column  *
     *            counts both greater than or equal to three                  *
     **************************************************************************/

    private boolean validateInOriginalImage(int[][] inOriginalImage)
    {
        boolean validInOriginalImage;
        int rowCount, colCount;

        rowCount = inOriginalImage.length;
        colCount = inOriginalImage[0].length;

        validInOriginalImage = false;
        if (rowCount >= IMAGE_ROW_COUNT_MIN && colCount >= IMAGE_COL_COUNT_MIN)
        {
            validInOriginalImage = true;
        }
        return validInOriginalImage;
    }
    
    /**************************************************************************
     * SUBMODULE: validateInKernel                                            *
     * IMPORT: inKernel (int[][])                                             *
     * EXPORT: validInKernel (Boolean)                                        *
     * ASSERTION: inKernel will be validated as having row and column         *
     *            counts both less than or equal to originalImage row and     *
     *            column count                                                *
     **************************************************************************/
    
    private boolean validateInKernel(int[][] inKernel)
    {
        boolean validInKernel;
        int rowCount, colCount;
        int rowCountIK, colCountIK;

        rowCount = originalImage.length;
        colCount = originalImage[0].length;

        rowCountIK = inKernel.length;
        colCountIK = inKernel[0].length;

        validInKernel = false;
        if (rowCount >= rowCountIK && colCount >= colCountIK)
        {
            validInKernel = true;
        }
        return validInKernel;
    }

    /**************************************************************************
     * SUBMODULE: deepCopy                                                    *
     * IMPORT: originalImage (int[][]), inOriginalImage (int[][])             *
     * EXPORT: none                                                           *
     * ASSERTION: originalImage will receive a copy of inOriginalImage        *
     *            contents                                                    *
     **************************************************************************/

    private void deepCopy(int[][] originalImage, int[][] inOriginalImage)
    {
        int rowCount, colCount;

        rowCount = inOriginalImage.length;
        colCount = inOriginalImage[0].length;

        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < colCount; j++)
            {
                originalImage[i][j] = inOriginalImage[i][j];
            }
        }
    }
}
