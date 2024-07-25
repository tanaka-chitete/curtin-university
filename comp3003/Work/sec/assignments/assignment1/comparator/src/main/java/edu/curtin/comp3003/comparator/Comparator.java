package edu.curtin.comp3003.comparator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Comparator extends Thread {
    private final TableView<Result>       resultsTable;
    private final ProgressBar             progressBar;
    private final File                    directory;

    private final List<File>              files;
    private final BlockingQueue<Contents> contentsQueue;
    private final BlockingQueue<Result>   resultsQueue;
    private       Thread                  searchThread; 
    private       Thread                  extractThread;
    private       Thread                  compareThread;
    private       Thread                  outputThread;
    private final ExecutorService         comparisonExecutorService; 
    private final ExecutorService         extractionExecutorService; 

    public Comparator(TableView<Result> resultsTable, 
                      ProgressBar progressBar, 
                      File directory) {
        this.resultsTable = resultsTable;
        this.progressBar = progressBar;
        this.directory = directory;
        files = new ArrayList<>();
        contentsQueue = new LinkedBlockingQueue<>();
        resultsQueue = new LinkedBlockingQueue<>();
        comparisonExecutorService = 
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        extractionExecutorService = 
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void run() {
        // Re-initialise progress bar and resutls table
        Platform.runLater(() -> {
            progressBar.setProgress(0.0);
            resultsTable.getItems().clear();
        });
        search();
        extract();
        compare();
        output();
    }

    @Override
    public void interrupt() {
        Runnable interruptRunnable = () -> {
            if (!searchThread.isInterrupted()) {
                searchThread.interrupt();
            }
    
            if (!extractThread.isInterrupted()) {
                extractThread.interrupt();
            }
    
            if (!compareThread.isInterrupted()) {
                compareThread.interrupt();
            }
    
            if (!outputThread.isInterrupted()) {
                outputThread.interrupt();
            }
    
            extractionExecutorService.shutdownNow();
            comparisonExecutorService.shutdownNow();
        };
        Thread interruptThread = new Thread(interruptRunnable);
        interruptThread.start();
    }

    private void search() {
        Runnable searchRunnable = () -> {
            try {
                // Recurse through the directory tree
                Files.walkFileTree(Paths.get(directory.toURI()), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                        // Add each file (represented as a path) to queue
                        File file = path.toFile();
                        // Ignore .DS_Store files found on macOS devices
                        if (!file.getName().equals(".DS_Store") && file.length() > 0) {
                            files.add(path.toFile());
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
        };
        searchThread = new Thread(searchRunnable);
        searchThread.start();
    }

    private void extract() {
        Runnable extractRunnable = () -> {
            try {
                searchThread.join();

                for (int i = 0; i < files.size(); i++) {
                    File file = files.get(i);
                    // Extract contents of file
                    Runnable extractionRunnable = () -> {
                        char[] contents = Helpers.extractContents(file);
                        contentsQueue.offer(new Contents(file.getName(), contents));
                    };
                    extractionExecutorService.execute(extractionRunnable);
                }

                // Shutdown executor service and wait for tasks currently in progress to finish
                extractionExecutorService.shutdown();
                try {
                    extractionExecutorService.awaitTermination(Long.MAX_VALUE, 
                                                               TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) { }
            } catch (InterruptedException e) {
                // stop() invoked; stop looping
            }
        };
        extractThread = new Thread(extractRunnable);
        extractThread.start();
    }

    private void compare() {
        Runnable compareRunnable = () -> {
            try {
                extractThread.join();

                List<Contents> contentsList = new ArrayList<>(contentsQueue);

                int numFiles = contentsQueue.size();
                int numComparisons = (int)(0.5 * ((numFiles * numFiles) - numFiles));
                double increment = 100.0 / (double)numComparisons;

                for (int i = 0; i < contentsList.size(); i++) {
                    for (int j = i + 1; j < contentsList.size(); j++) {
                        Contents contents1 = contentsList.get(i);
                        Contents contents2 = contentsList.get(j);

                        Runnable comparisonRunnable = () -> {
                            double similarity = Helpers.computeSimilarity(contents1.getContents(), 
                                                                          contents2.getContents());
                            resultsQueue.offer(
                                new Result(contents1.getFilename(), 
                                                     contents2.getFilename(), 
                                                     similarity));

                            Platform.runLater(() -> {
                                progressBar.setProgress(progressBar.getProgress() + increment);
                            });
                        };
                        comparisonExecutorService.execute(comparisonRunnable);
                    }
                }

                // Shutdown executor service and wait for tasks currently in progress to finish
                comparisonExecutorService.shutdown();
                try {
                    comparisonExecutorService.awaitTermination(Long.MAX_VALUE, 
                                                               TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) { }
            } catch (InterruptedException e) { }
        };
        compareThread = new Thread(compareRunnable);
        compareThread.start();
    }

    private void output() {
        Runnable outputRunnable = () -> {
            try {
                compareThread.join();

                List<Result> resultsList = new ArrayList<>(resultsQueue);

                StringBuilder resultsStringBuilder = new StringBuilder();

                for (int i = 0; i < resultsList.size(); i++) {
                    Result result = resultsList.get(i);

                    resultsStringBuilder.append(result.toString() + "\n");
                    if (result.getSimilarity() > 0.5) {
                        Platform.runLater(() -> {
                            resultsTable.getItems().add(result);
                        });
                    }
                }

                PrintWriter resultsPrintWriter = new PrintWriter(directory + "/results.csv");
                resultsPrintWriter.println(resultsStringBuilder);

                resultsPrintWriter.close();
            } catch (InterruptedException e) {
                // stop() invoked; stop looping
            } catch (IOException e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
        };
        outputThread = new Thread(outputRunnable);
        outputThread.start();
    }
}
