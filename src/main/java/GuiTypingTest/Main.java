package GuiTypingTest;

import javax.swing.SwingUtilities;

/**
 * The Main class is the entry point for the Typing Tester application.
 * It schedules the creation and display of the application's GUI.
 */
public class Main {
    /**
     * The main method schedules a job for the event-dispatching thread,
     * creating and showing this application's GUI.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread
        // Create an instance of TypingTesterGUI
        SwingUtilities.invokeLater(TypingTesterGUI::new);
    }
}