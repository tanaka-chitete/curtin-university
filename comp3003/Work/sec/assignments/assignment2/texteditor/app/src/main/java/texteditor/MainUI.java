/**
 * Adapted from D. Cooper, 
 * Worksheet 8. Internationalisation (I18N)
 * (Accessed 23 October 2021)
 */

package texteditor;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.text.JTextComponent.KeyBinding;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Controls the main window, displaying the text file contents, a toolbar (with 'load', 'save'
 * and 'mechanisms'
 */
public class MainUI implements Control {
    private Stage stage;
    private ResourceBundle bundle;
    private FileUI fileUI;
    private MechanismsUI mechanismsUI;
    private TextArea textArea;
    private List<KeyMap> keyMaps;
    private ToolBar toolBar;

    public MainUI(Stage stage, ResourceBundle bundle, FileUI fileUI, MechanismsUI mechanismsUI, TextArea textArea, List<KeyMap> keyMaps)
    {
        this.stage = stage;
        this.bundle = bundle;
        this.fileUI = fileUI;
        this.mechanismsUI = mechanismsUI;
        this.textArea = textArea;
        this.keyMaps = keyMaps;
    }

    public void display() 
    {
        stage.setTitle(bundle.getString("editor"));
        stage.setMinWidth(800);

        // Create toolbar
        Button loadBtn = new Button(bundle.getString("load")); 
        Button saveBtn = new Button(bundle.getString("save")); 
        Button mechanismsBtn = new Button(bundle.getString("mechanisms")); 
        toolBar = new ToolBar(loadBtn, saveBtn, mechanismsBtn);

        // Subtle user experience tweaks
        toolBar.setFocusTraversable(false);
        toolBar.getItems().forEach(btn -> btn.setFocusTraversable(false));
        textArea.setStyle("-fx-font-family: 'monospace'"); // Set the font
        
        // Add the main parts of the UI to the window.
        BorderPane mainBox = new BorderPane();
        mainBox.setTop(toolBar);
        mainBox.setCenter(textArea);
        Scene scene = new Scene(mainBox);        
        
        // Button event handlers.
        loadBtn.setOnAction(event -> fileUI.load());
        saveBtn.setOnAction(event -> fileUI.save());
        mechanismsBtn.setOnAction(event -> mechanismsUI.showDialog(this));
        
        // TextArea event handlers & caret positioning.
        textArea.textProperty().addListener((object, oldValue, newValue) -> 
        {
            System.out.println("caret position is " + textArea.getCaretPosition() + 
                               "; text is\n---\n" + newValue + "\n---\n");
        });
        
        for (KeyMap keyMap : keyMaps)
        {
            KeyCombination keyCombination = KeyCombination.keyCombination(keyMap.getKeyBinding());
            scene.getAccelerators().put(keyCombination, () ->
            {
                if (keyMap.getOperation().equals("insert"))
                {
                    insert(textArea, keyMap.getOperand(), keyMap.getPosition());
                }
                else // else if (keyMap.getOperation().equals("delete"))
                {
                    delete(textArea, keyMap.getOperand(), keyMap.getPosition());
                }
            });
        }

        // Example global keypress handler.
        scene.setOnKeyPressed(keyEvent -> 
        {
            // See the documentation for the KeyCode class to see all the available keys.
            for (KeyMap keyMap : keyMaps)
            {
                if (keyEvent.isConsumed()) // Stop this event from processed multiple times
                {
                    KeyCombination keyCombination = KeyCombination.keyCombination(keyMap.getKeyBinding());
                    if (keyCombination.match(keyEvent))
                    {
                        if (keyMap.getOperation().equals("insert"))
                        {
                            insert(textArea, keyMap.getOperand(), keyMap.getPosition());
                        }
                        else // else if (keyMap.getOperation().equals("delete"))
                        {
                            delete(textArea, keyMap.getOperand(), keyMap.getPosition());
                        }
                        break;
                    }
                }
            }
        });
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void insert(TextArea textArea, String operand, String linePosition)
    {
        if (linePosition.equals("at caret"))
        {
            textArea.insertText(textArea.getCaretPosition(), operand);
        }
        else // if (linePosition.equals("at start of line"))
        {
            // Move backwards through textArea until \n is found
            String contents = textArea.getText();
            int i;
            if (textArea.getCaretPosition() >= contents.length())
            {
                i = textArea.getCaretPosition() - 1; // In case caret is at end of contents
            }
            else 
            {
                i = textArea.getCaretPosition();
            }
            while (i > 0 && contents.charAt(i) != '\n')
            {
                i--;
            }

            if (contents.charAt(i) != '\n')
            {
                i--; // Ensure we are at the very start of the textBox
            }
            int oldCaretPosition = textArea.getCaretPosition();
            textArea.insertText(i + 1, operand);
            textArea.positionCaret(oldCaretPosition + operand.length()); // Move caret ahead, the length of the operand
        }
    }

    private void delete(TextArea textArea, String operand, String linePosition)
    {
        String contents = textArea.getText();
        int caretPosition = textArea.getCaretPosition();
        // Ensure there is contents to delete and the caret isn't too far back
        if (contents.length() >= operand.length() && 
        caretPosition >= operand.length()) 
        {
            if (linePosition.equals("at caret"))
            {
                // The substring before the caret matches the operand
                if (contents.substring(caretPosition - operand.length(), 
                caretPosition).equals(operand))
                {
                    String newContents = contents.substring(0, caretPosition - operand.length()) +
                    contents.substring(caretPosition);
                    textArea.setText(newContents);
                    textArea.positionCaret(caretPosition - operand.length());
                }
            }
            else // if (linePosition.equals("at start of line"))
            {
                // Move backwards through textArea until \n is found
                contents = textArea.getText();
                int i;
                if (textArea.getCaretPosition() >= contents.length())
                {
                    i = textArea.getCaretPosition() - 1; // In case caret is at end of contents
                }
                else 
                {
                    i = textArea.getCaretPosition();
                }
                while (i > 0 && contents.charAt(i) != '\n')
                {
                    i--;
                }

                if (contents.charAt(i) == '\n')
                {
                    i++; // Move one character ahead of the new line
                }
                // Substring at the start of the line matches the operand
                if (contents.substring(i, i + operand.length()).equals(operand))
                {
                    String newContents = contents.substring(0, i) +
                    contents.substring(i + operand.length());
                    textArea.setText(newContents);
                    // Move caret back, the length of the operand
                    textArea.positionCaret(caretPosition - operand.length());     
                }
            }
        }
    }

    @Override 
    public void registerDateHandler(DateHandler callback)
    {
        Button dateBtn = new Button(bundle.getString("date"));
        toolBar.getItems().add(dateBtn);
        dateBtn.setOnAction(event -> callback.getDate());
    }

    @Override 
    public void registerFindHandler(FindHandler callback)
    {
        Button findBtn = new Button(bundle.getString("find"));
        toolBar.getItems().add(findBtn);
        findBtn.setOnAction(event -> {
            String inputStr = showTextInputDialog();
            if (inputStr != null) 
            {
                callback.find(textArea.getText(), textArea.getCaretPosition(), inputStr);
            }
        });
    }

    @Override
    public void registerEmojiHandler(EmojiHandler callback)
    {
        textArea.textProperty().addListener((object, oldValue, newValue) -> 
        {
            callback.emoji(textArea.getText(), textArea.getCaretPosition());
        });
    }

    @Override
    public Locale getLocale()
    {
        return bundle.getLocale();
    }

    @Override
    public void displayDate(String date)
    {
        int caretPosition = textArea.getCaretPosition();
        textArea.insertText(caretPosition, date);
        textArea.positionCaret(caretPosition + date.length()); // Move caret forward, date length
    }

    @Override 
    public void displayFind(int startIndex, int endIndex)
    {
        textArea.selectRange(startIndex, endIndex);
    }

    @Override
    public void displayEmoji(String updatedContents)
    {
        textArea.setText(updatedContents);
    }

    private String showTextInputDialog()
    {
        // TextInputDialog is a subclass of Dialog that just presents a single text field.
    
        var dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("textInput"));
        dialog.setHeaderText(bundle.getString("input"));
        
        // 'showAndWait()' opens the dialog and waits for the user to press the 'OK' or 'Cancel' button. It returns an Optional, which is a whole other discussion, but we can call 'orElse(null)' on that to get the actual string entered if the user pressed 'OK', or null if the user pressed 'Cancel'.
        
        var inputStr = dialog.showAndWait().orElse(null);

        return inputStr;
    }
}
