// Parts of this file comprise externally-obtained code

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.*;

public class JSONObjectIO {
    /*
     * NAME: readFromURL
     * IMPORT(S): URL (String)
     * EXPORT(S): JSONObject (JSONObject)
     * PURPOSE: Read JSONArrays from URLS
     * CREATION: 05/10/2020
     * LAST MODIFICATION: 10/11/2020
     */

    // Adapted from Rolland Illig
    // https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
    // Accessed 05/10/2020
    public static JSONObject readFromURL(String URL) throws IOException {
        InputStream inputStream = new URL(URL).openStream();
        Charset charset = Charset.forName("UTF-8");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String JSONString = _readerToString(bufferedReader);
        JSONObject JSONObject = new JSONObject(JSONString);

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        return JSONObject;
    }
    // End of code adapted from Rolland Illig

    /*
     * NAME: readFromFile
     * IMPORT(S): filename (String)
     * EXPORT(S): JSONObject (JSONObject)
     * PURPOSE: Read JSONObjects from files
     * CREATION: 14/10/2020
     * LAST MODIFICATION: 10/11/2020
     */

    public static JSONObject readFromFile(String filename) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String JSONString = _readerToString(bufferedReader);
        JSONObject JSONObject = new JSONObject(JSONString);

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        return JSONObject;
    }

    /*
     * NAME: writeToFile
     * IMPORT(S): JSONObject (JSONObject), filename (String)
     * EXPORT(S): NONE
     * PURPOSE: Write JSONObjects to files
     * CREATION: 15/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void writeToFile(JSONObject JSONObject, String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);

        String JSONString = JSONObject.toString();
        printWriter.print(JSONString);

        if (printWriter != null) {
            printWriter.flush();
            printWriter.close();
        }
    }

    /*
     * NAME: _readerToString
     * IMPORT(S): URL (String)
     * EXPORT(S): reader (Reader)
     * PURPOSE: Parse readers' contents to strings
     * CREATION: 05/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    // Adapted from Rolland Illig
    // https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
    // Accessed 05/10/2020
    private static String _readerToString(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int in;
        while ((in = reader.read()) != -1) {
            stringBuilder.append((char) in);
        }

        String stringBuilderString = stringBuilder.toString();

        return stringBuilderString;
    }
    // End of code adapted from Rolland Illig
}