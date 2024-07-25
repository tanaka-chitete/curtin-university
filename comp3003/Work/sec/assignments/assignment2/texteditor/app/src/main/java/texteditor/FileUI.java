/**
 * Adapted from D. Cooper, 
 * Worksheet 8. Internationalisation (I18N)
 * (Accessed 23 October 2021)
 */

package texteditor;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** 
 * Controls those parts of the user interface relating to loading/saving text files. For 
 * both loading and saving, we first display a 'file chooser' window, and then a second dialog box
 * to select the file encoding.
 */
public class FileUI
{
    private static final int SPACING = 8;
    
    private Stage stage;
    private ResourceBundle bundle;
    private FileIO fileIO;
    private TextArea textArea;
    private FileChooser fileDialog;
    private Dialog<String> encodingDialog;
    
    public FileUI(Stage stage, ResourceBundle bundle, FileIO fileIO, TextArea textArea)
    {
        this.stage = stage;
        this.bundle = bundle;
        this.fileIO = fileIO;
        this.textArea = textArea;
        fileDialog = new FileChooser();
    }
    
    /**
     * Internal method for displaying the encoding dialog and retrieving the name of the chosen 
     * encoding.
     */
    private String getEncoding()
    {
        if(encodingDialog == null)
        {
            var encodingComboBox = new ComboBox<String>();
            var content = new FlowPane();
            encodingDialog = new Dialog<>();
            encodingDialog.setTitle(bundle.getString("fileEncoding"));
            encodingDialog.getDialogPane().setContent(content);
            encodingDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);        
            encodingDialog.setResultConverter(
                btn -> (btn == ButtonType.OK) ? encodingComboBox.getValue() : null);
            
            content.setHgap(SPACING);
            content.getChildren().setAll(new Label(bundle.getString("encoding")), encodingComboBox);
            
            encodingComboBox.getItems().setAll("UTF-8", "UTF-16", "UTF-32");
            encodingComboBox.setValue("UTF-8");
        }        
        return encodingDialog.showAndWait().orElse(null);
    }

    /**
     * Asks the user to choose a file to load, then an encoding, then loads the file contents and 
     * updates the text area.
     */
    public void load()
    {    
        File f = fileDialog.showOpenDialog(stage);
        if(f != null)
        {
            String encoding = getEncoding();
            if(encoding != null)
            {
                try
                {
                    textArea.setText(fileIO.load(f, encoding));
                }
                catch(IOException e)
                {
                    new Alert(
                        Alert.AlertType.ERROR, 
                        String.format(bundle.getString("fileLoadError")),
                        ButtonType.CLOSE
                    ).showAndWait();
                }                
            }
        }
    }
    
    /**
     * Asks the user to choose a filename to save the file under, then an encoding, then 
     * saves the file contents to the chosen file in the chosen encoding.
     */
    public void save()
    {
        File f = fileDialog.showSaveDialog(stage);
        if(f != null)
        {
            String encoding = getEncoding();
            if(encoding != null)
            {
                try
                {
                    fileIO.save(f, textArea.getText(), encoding);
                }
                catch(IOException e)
                {
                    new Alert(
                        Alert.AlertType.ERROR, 
                        String.format(bundle.getString("fileSaveError")),
                        ButtonType.CLOSE
                    ).showAndWait();
                }
            }
        }
    }
}
