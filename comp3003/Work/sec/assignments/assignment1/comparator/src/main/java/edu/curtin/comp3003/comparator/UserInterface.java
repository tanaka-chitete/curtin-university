package edu.curtin.comp3003.comparator;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

public class UserInterface extends Application {   
    private TableView<Result> resultTable = new TableView<>();
    private ProgressBar progressBar = new ProgressBar();
    private Comparator comparator;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Comparator");
        stage.setMinWidth(600);

        // Create toolbar
        Button compareBtn = new Button("Compare...");
        Button stopBtn = new Button("Stop");
        ToolBar toolBar = new ToolBar(compareBtn, stopBtn);
        
        // Set up button event handlers.
        compareBtn.setOnAction(event -> compare(stage));
        stopBtn.setOnAction(event -> stop());
        
        // Initialise progressbar
        progressBar.setProgress(0.0);
        
        TableColumn<Result,String> file1Col = new TableColumn<>("File 1");
        TableColumn<Result,String> file2Col = new TableColumn<>("File 2");
        TableColumn<Result,String> similarityCol = new TableColumn<>("Similarity");
        
        // The following tell JavaFX how to extract information from a ComparisonResult 
        // object and put it into the three table columns.
        file1Col.setCellValueFactory(   
            (cell) -> new SimpleStringProperty(cell.getValue().getFile1()));
        file2Col.setCellValueFactory(   
            (cell) -> new SimpleStringProperty(cell.getValue().getFile2()));
        similarityCol.setCellValueFactory(  
            (cell) -> new SimpleStringProperty(
                String.format("%.1f%%", cell.getValue().getSimilarity() * 100.0)));
          
        // Set and adjust table column widths.
        file1Col.prefWidthProperty().bind(resultTable.widthProperty().multiply(0.40));
        file2Col.prefWidthProperty().bind(resultTable.widthProperty().multiply(0.40));
        similarityCol.prefWidthProperty().bind(resultTable.widthProperty().multiply(0.20));            
        
        // Add the columns to the table.
        resultTable.getColumns().add(file1Col);
        resultTable.getColumns().add(file2Col);
        resultTable.getColumns().add(similarityCol);

        // Add the main parts of the UI to the window.
        BorderPane mainBox = new BorderPane();
        mainBox.setTop(toolBar);
        mainBox.setCenter(resultTable);
        mainBox.setBottom(progressBar);
        Scene scene = new Scene(mainBox);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("Stopping comparison...");
        if (comparator != null) {
            comparator.interrupt();
        }
    }

    private void compare(Stage stage) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File("."));
        dc.setTitle("Choose directory");
        File directory = dc.showDialog(stage);

        if (directory != null) {
            System.out.println("Comparing files within " + directory + "...");

            comparator = new Comparator(resultTable, progressBar, directory);
            comparator.start();
        } else {
            System.out.println("No directory was chosen");
        }
    }
}
