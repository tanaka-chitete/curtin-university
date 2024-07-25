package texteditor;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MechanismsUI 
{
    private Stage stage;
    private ResourceBundle bundle;
    private MechanismsIO mechanismsIO;
    private ObservableList<String> list;
    private FileChooser scriptDialog;

    public MechanismsUI(Stage stage, ResourceBundle bundle, MechanismsIO mechanismsIO)
    {
        this.stage = stage;
        this.bundle = bundle;
        this.mechanismsIO = mechanismsIO;
        list = FXCollections.observableArrayList();
        scriptDialog = new FileChooser();
    }

    public void showDialog(MainUI mainUI)
    {        
        Button addPluginBtn = new Button(bundle.getString("addPlugin"));
        Button addScriptBtn = new Button(bundle.getString("addScript"));
        ToolBar toolBar = new ToolBar(addPluginBtn, addScriptBtn);
        
        addPluginBtn.setOnAction(event -> addPlugin(mainUI));
        addScriptBtn.setOnAction(event -> addScript(mainUI));
        
        // FYI: 'ObservableList' inherits from the ordinary List interface, but also works as a subject for any 'observer-pattern' purposes; e.g., to allow an on-screen ListView to display any changes made to the list as they are made.
        
        ListView<String> listView = new ListView<>(list);        
        
        BorderPane box = new BorderPane();
        box.setTop(toolBar);
        box.setCenter(listView);
        
        Dialog dialog = new Dialog();
        dialog.setTitle(bundle.getString("mechanisms"));
        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    private void addPlugin(MainUI mainUI) 
    {
        // TextInputDialog is a subclass of Dialog that just presents a single text field.
    
        var dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("addPlugin"));
        dialog.setHeaderText(bundle.getString("pluginNameEntry"));
        
        // 'showAndWait()' opens the dialog and waits for the user to press the 'OK' or 'Cancel' button. It returns an Optional, which is a whole other discussion, but we can call 'orElse(null)' on that to get the actual string entered if the user pressed 'OK', or null if the user pressed 'Cancel'.
        
        var inputStr = dialog.showAndWait().orElse(null);
        if(inputStr != null)
        {
            if(list.contains(inputStr))
            {
                new Alert(
                    Alert.AlertType.ERROR,
                    bundle.getString("pluginAddingError"),
                    ButtonType.CLOSE
                ).showAndWait();
            }
            else
            {
                try
                {
                    mechanismsIO.addPlugin(mainUI, inputStr);
                    // Add the name of the plugin to the list of added plugins/scripts
                    list.add(inputStr); 
                }
                catch (ReflectiveOperationException e)
                {
                    e.printStackTrace();
                    new Alert(
                        Alert.AlertType.ERROR,
                        bundle.getString("pluginAddingError"),
                        ButtonType.CLOSE
                    ).showAndWait();
                }
            }
        }
    }

    private void addScript(MainUI mainUI)
    {
        scriptDialog.setTitle(bundle.getString("addScript"));
    
        File f = scriptDialog.showOpenDialog(stage);
        if(f != null)
        {
            try
            {
                mechanismsIO.addScript(mainUI, f, "UTF-8");
                // Add the name of the script to the list of added plugins/scripts
                list.add(f.getName());
            }
            catch(IOException e)
            {
                new Alert(
                    Alert.AlertType.ERROR, 
                    String.format(bundle.getString("scriptAddingError")),
                    ButtonType.CLOSE
                ).showAndWait();
            }                
        }        
    }
}
