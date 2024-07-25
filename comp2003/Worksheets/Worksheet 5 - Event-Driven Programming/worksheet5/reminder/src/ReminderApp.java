import javax.swing.*;

/**
 * A reminder application.
 * @author Dave
 */
public class ReminderApp 
{
    public static void main(String[] args)
    {
        final ReminderList reminders = new ReminderList();
        final Controller controller = new Controller(reminders);
            
        // Make sure all GUI-related stuff happens within the "Event Dispatch Thread". (Standard practice for the Java Swing GUI.)
        SwingUtilities.invokeLater(
            new Runnable() 
            {
                public void run() 
                {   
                    // Create a window (hidden to begin with)
                    MainWindow window = new MainWindow(controller);

                    // Create file manager
                    FileManager manager = new FileManager(controller);

                    // Add both window and manager as observers
                    reminders.addObserver(window);
                    reminders.addObserver(manager);
                                        
                    // Exit when the exit button is pressed.
                    window.setVisible(true);
                }
            }
        );
    }
}
