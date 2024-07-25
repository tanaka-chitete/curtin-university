package edu.curtin.comp3003.filesearcher;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.swing.SwingUtilities;

public class FSFilter 
{
    private String searchPath;
    private String searchTerm;
    private FSUserInterface ui;
    private ExecutorService executorService;

    private Thread searchThread;

    public FSFilter(String searchPath, String searchTerm, FSUserInterface ui)
    {
        this.searchPath = searchPath;
        this.searchTerm = searchTerm;
        this.ui = ui;
        executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());
    }

    public void start() 
    {
        search();
    }

    private void search() 
    {
        Runnable searchRunnable = () ->
        {
            try
            {
                // Recurse through the directory tree
                Files.walkFileTree(Paths.get(searchPath), new SimpleFileVisitor<Path>()
                {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    {
                        executorService.execute(() -> filter(file));

                        return FileVisitResult.CONTINUE;
                    }
                });
            }
            catch(IOException e)
            {
                // This error handling is a bit quick-and-dirty, but it will suffice here.
                ui.showError(e.getClass().getName() + ": " + e.getMessage());
            }
        };
        searchThread = new Thread(searchRunnable);
        searchThread.start();
    }

    // Adapted from Amir Afghani
    // https://stackoverflow.com/questions/15577688/search-a-file-for-a-string-and-return-that-string-if-found
    // Accessed 25/08/2021
    private void filter(Path file)
    {
        try {
            Scanner scanner = new Scanner(file);
            boolean isMatch = false;
            while (!isMatch && scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                if (line.contains(searchTerm)) 
                {
                    isMatch = true;
                    SwingUtilities.invokeLater(() -> 
                    {
                        ui.addResult(file.toString());
                    });
                }
            }
        } 
        catch (IOException e) 
        {
            // This error handling is a bit quick-and-dirty, but it will suffice here.
            ui.showError(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    // End of code adapted from above source
}
