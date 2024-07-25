// Parts of this file comprise externally-obtained code

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.*;

public class IOJSONObject {
    /*
     * NAME: readFromURL
     * IMPORT(S): URL (String)
     * EXPORT(S): JSONObject (JSONObject)
     * PURPOSE: Read JSONArrays from URLS
     * CREATION: 05/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    // Adapted from Rolland Illig
    // https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
    // Accessed 05/10/2020
    public static JSONObject readFromURL(String URL) {
        JSONObject JSONObject = null;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new URL(URL).openStream();
            Charset charset = Charset.forName("UTF-8");
            inputStreamReader = new InputStreamReader(inputStream, charset);
            bufferedReader = new BufferedReader(inputStreamReader);
            String JSONString = _readerToString(bufferedReader);
            JSONObject = new JSONObject(JSONString);
        } 
        catch (IOException e) {
            System.out.println("Could not parse reader contents\n");
        }
        catch (JSONException e) {
            System.out.println("Could not instantiate JSON Object\n");
        }
        finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e) {
                System.out.println("Could not close readers\n");
            }
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
     * LAST MODIFICATION: 01/11/2020
     */

    public static JSONObject readFromFile(String filename) {
        JSONObject JSONObject = null;

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(filename);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String JSONString = _readerToString(bufferedReader);
            JSONObject = new JSONObject(JSONString);
            fileInputStream.close();
        }
        catch (IOException e) {
            System.out.println("Reader parsing unsuccessful\n");
        }
        catch (JSONException e) {
            System.out.println("JSON Object instantiation unsuccessful\n");
        }
        finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e) {
                System.out.println("Could not close readers\n");
            }
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

    public static void writeToFile(JSONObject JSONObject, String filename) {
        PrintWriter printWriter = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            printWriter = new PrintWriter(fileOutputStream);

            String JSONString = JSONObject.toString();
            printWriter.print(JSONString);
        }
        catch (FileNotFoundException e){
            System.out.println("File creation unsuccessful\n");
        }
        finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
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

        String stringBuilderStr = stringBuilder.toString();

        return stringBuilderStr;
    }
    // End of code adapted from Rolland Illig
}