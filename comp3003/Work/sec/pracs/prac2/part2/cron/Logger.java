import java.io.*;

/**
 * The logger is in charge of writing output to 'cron.log'. It does this in its own thread, but 
 * assumes that other threads will call the setMessage() in order to provide messages to log. (You 
 * need to fill in the details!)
 */
public class Logger
{
    private String nextMessage;
    private Thread thread;
    private Object monitor;
    
    public Logger()
    {
        nextMessage = null;
        monitor = new Object();
    }

    public void logMessage(String msg) throws InterruptedException
    {
        synchronized(monitor)
        {
            if (nextMessage != null) 
            {
                monitor.wait();
            }
            nextMessage = msg;
            monitor.notify();
        }
    }

    public void start()
    {
        Runnable logger = () ->
        {
            try
            {
                synchronized(monitor) 
                {
                    while(true) 
                    {
                        if (nextMessage == null) 
                        {
                            monitor.wait();
                        }
                        try(PrintWriter writer = 
                        new PrintWriter(new FileWriter("cron.log", true)))
                        {
                            writer.println(nextMessage);
                        }
                        nextMessage = null;
    
                        monitor.notify();
                    }
                }
            }
            catch (IOException e) 
            {
                System.out.println("Failed to log message");
            }
            catch (InterruptedException e) 
            {
                // stop() invoked; stop loop.
            }
        };
        thread = new Thread(logger);
        thread.start();
    }
    
    public void stop()
    {
        thread.interrupt();
    }
}
