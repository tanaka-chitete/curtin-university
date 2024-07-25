package texteditor;

import org.python.core.*;
import org.python.util.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javafx.collections.ObservableList;

public class MechanismsIO {
    public void addPlugin(MainUI mainUI, String clsName) throws ReflectiveOperationException
    {
        // Find the user-specified class name (fully-qualified)
        Class<?> cls = Class.forName(clsName); 
        // Get the no-args constructor
        Constructor<?> cons = cls.getConstructor(); 
        // Get the start method (which takes a control object)
        Method startMthd = cls.getDeclaredMethod("start", Control.class); 
        // Create a new instance of the plugin (with the default constructor)
        Object instance = (Object) cons.newInstance(); 
        // Call the start method of the plugin
        startMthd.invoke(instance, mainUI); 
    }

    /**
     * Adapted from D. Cooper,
     * Worksheet 6b. Plugins and Scripting
     * (Accessed 27/10/2021)
     */
    public void addScript(MainUI mainUI, File file, String encoding) throws IOException
    {
        // Load the script
        String script = new FileIO().load(file, encoding);
        // Initialise the interpreter
        PythonInterpreter interpreter = new PythonInterpreter();
        // Bind the API to the script environment
        interpreter.set("api", mainUI);
        // Run the script
        interpreter.exec(script);
    }
    /** End of code adapted from above source */
}
