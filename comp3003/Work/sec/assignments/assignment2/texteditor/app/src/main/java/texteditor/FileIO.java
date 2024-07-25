/**
 * Adapted from D. Cooper, 
 * Worksheet 8. Internationalisation (I18N)
 * (Accessed 23 October 2021)
 */

package texteditor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Performs the reading and writing of text files.
 */
public class FileIO
{
    /**
     * Loads the contents of a given text file
     */
    public String load(File file, String encoding) throws IOException
    {
        Path path = Paths.get(file.toURI());
        byte[] data = Files.readAllBytes(path);
        return new String(data, encoding);
    }

    /**
     * Writes the contents of the text area to a file
     */
    public void save(File file, String contentsString, String encoding) throws IOException
    {
        try(PrintWriter pw = new PrintWriter(file))
        {
            byte[] contentsBytes = contentsString.getBytes(encoding);
            contentsString = new String(contentsBytes, encoding);
            pw.println(contentsString);
        }
    }
}
