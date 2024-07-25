/**
 * Adapted from D. Cooper, 
 * Worksheet 8. Internationalisation (I18N)
 * (Accessed 23 October 2021)
 */

package texteditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.css.CssParser.ParseError;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Entry point for the text editor. It can be invoked with the command-line parameter 
 * --locale=[value].
 */
public class App extends Application
{
    static List<KeyMap> keyMaps;
    public static void main(String[] args)
    {
        try {
            Parser parser = new Parser(new FileInputStream("keymap"));
            keyMaps = parser.parse();
            Application.launch(args);
        }
        catch (IOException e)
        {
            System.out.println("Unable to locate keymap file");
            Platform.exit();
        }
        catch (ParseException e)
        {
            System.out.println("Unable to parse keymap file");
            Platform.exit();
        }
    }
    
    @Override
    public void start(Stage stage)
    {
        Locale localeObject = null;
        String localeString = getParameters().getNamed().get("locale");
        if(localeString == null || localeString.equals("en_AU")) 
        {
            localeObject = new Locale("en", "AU");
        }
        else if (localeString.equals("fr_FR")) 
        {
            localeObject = new Locale("fr", "FR");
        }

        if (localeObject == null) 
        {
            System.out.println("usage: ./gradlew run\n" + 
                "usage: ./gradlew run --args=\"--locale_en_AU\"\n" + 
                "usage: ./gradlew run --args=\"--locale_fr_FR\"");
            Platform.exit();
        }
        else 
        {
            ResourceBundle bundle = ResourceBundle.getBundle("texteditor/Bundle", localeObject);
            TextArea textArea = new TextArea();
            FileIO fileIO = new FileIO();
            FileUI fileUI = new FileUI(stage, bundle, fileIO, textArea);
            MechanismsIO mechanismsIO = new MechanismsIO();
            MechanismsUI mechanismsUI = new MechanismsUI(stage, bundle, mechanismsIO);
            new MainUI(stage, bundle, fileUI, mechanismsUI, textArea, keyMaps).display();
        }
    }
}
