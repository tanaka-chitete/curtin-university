import java.util.*;

/**
 * The scheduler keeps track of all the jobs, and runs each one at the appropriate time. (You need
 * to fill in the details!)
 */
public class Scheduler
{
    private final List<Job> jobs;
    private Thread thread;
    private Object mutex;

    public Scheduler() 
    {
        jobs = new ArrayList<Job>();
        mutex = new Object();
    }

    public void addJob(Job newJob)
    {
        synchronized(mutex) 
        {
            jobs.add(newJob);
        }
    }

    public void start()
    {
        Runnable scheduler = () ->
        {
            try 
            {
                int elapsedTime = 0;
                while (true) 
                {
                    synchronized(mutex) 
                    {
                        for (Job j : jobs) 
                        {
                            if (elapsedTime % j.getDelay() == 0) 
                            {
                                Thread jobThread = new Thread(j);
                                jobThread.start();
                            }
                        }
                    }

                    Thread.sleep(1_000L);
                    elapsedTime++;
                }
            }
            catch (InterruptedException e) 
            {
                // stop() invoked; stop loop.
            }
        };
        thread = new Thread(scheduler);
        thread.start();
    }

    public void stop()
    {
        thread.interrupt();
    }
}
