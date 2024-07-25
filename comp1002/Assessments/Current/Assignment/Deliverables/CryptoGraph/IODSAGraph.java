import java.io.*;

public class IODSAGraph {
    /*
     * NAME: read
     * IMPORT(S): filename (String)
     * EXPORT(S): inGraph (DSAGraph)
     * PURPOSE: Deserialise DSAGraphs from files
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    public static DSAGraph read(String filename) {
        DSAGraph inGraph = new DSAGraph();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            inGraph = (DSAGraph) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("DSAGraph class not found: " + e.getMessage() + "\n");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found\n");
        }
        catch (IOException e) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (IOException e2) {
                }
            }
        }

        return inGraph;
    }

    /*
     * NAME: write
     * IMPORT(S): graph (DSAGraph), filename (String)
     * EXPORT(S): NONE
     * PURPOSE: Serialise DSAGraphs to files
     * CREATION: 15/09/2020
     * LAST MODIFICATION: 15/09/2020
     */

    public static void write(DSAGraph graph, String filename) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(graph);
            objectOutputStream.close();
        }
        catch (IOException e) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException e2) {
                }
            }
        }
    }
}