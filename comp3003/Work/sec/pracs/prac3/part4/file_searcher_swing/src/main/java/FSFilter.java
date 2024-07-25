package edu.curtin.comp3003.filesearcher;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

public class FSFilter 
{
    private String searchPath;
    private String searchTerm;
    private FSUserInterface ui;
    BlockingQueue<String> files = new ArrayBlockingQueue<>(500);

    private Thread searchThread;
    private Thread filterThread;

    public FSFilter(String searchPath, String searchTerm, FSUserInterface ui)
    {
        this.searchPath = searchPath;
        this.searchTerm = searchTerm;
        this.ui = ui;
    }

    public void start() 
    {
        search();
        filter();
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
                        try {
                            // Add each file to blocking queue
                            files.put(file.toString());
                        } catch (InterruptedException e) { }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
            catch(IOException e)
            {
                // This error handling is a bit quick-and-dirty, but it will suffice here.
                ui.showError(e.getClass().getName() + ": " + e.getMessage());
            }

            return;
        };
        searchThread = new Thread(searchRunnable);
        searchThread.start();
    }

    private void filter()
    {
        Runnable filterRunnable = () ->
        {
            try 
            {
                while(true)
                {
                    // Remove each from blocking queue
                    String fileStr = files.take();
                    if(fileStr.contains(searchTerm))
                    {
                        SwingUtilities.invokeLater(() ->
                        {
                            ui.addResult(fileStr);
                        });
                    }
                }
            }
            catch(InterruptedException e)
            {
                // stop() invoked; stop looping.
            }

            return;
        };
        filterThread = new Thread(filterRunnable);
        filterThread.start();
    }
}
