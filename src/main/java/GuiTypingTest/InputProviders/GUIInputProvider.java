package GuiTypingTest.InputProviders;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * GUIInputProvider is an implementation of the InputProvider interface.
 * It provides user input through a GUI with a JTextField and a JButton.
 */
public class GUIInputProvider implements InputProvider {
    private final JTextField inputField;
    private final JButton submitButton;
    private JTextArea displayArea;

    /**
     * Constructor for GUIInputProvider.
     * Initializes the input field and submit button, and sets up the action listener.
     *
     * @param inputField   the JTextField for user input
     * @param submitButton the JButton to submit the input
     */
    public GUIInputProvider(JTextField inputField, JButton submitButton) {
        this.inputField = inputField;
        this.submitButton = submitButton;
        // Trigger the submit button click when Enter is pressed in the input field
        this.inputField.addActionListener(e -> submitButton.doClick());
    }

    /**
     * Gets the display area associated with this input provider.
     *
     * @return the JTextArea used for displaying text
     */
    public JTextArea getDisplayArea() {
        return displayArea;
    }

    /**
     * Sets the display area for this input provider.
     *
     * @param displayArea the JTextArea to be set
     */
    public void setDisplayArea(JTextArea displayArea) {
        this.displayArea = displayArea;
    }

    /**
     * Gets the user input from the input field.
     * Clears the input field after retrieving the input.
     *
     * @return the user input as a String
     */
    @Override
    public String getUserInput() {
        String input = inputField.getText();
        inputField.setText(""); // Clear the input field
        return input;
    }

    /**
     * Sets the action to be performed when the submit button is clicked.
     * Removes any existing action listeners before adding the new one.
     *
     * @param action the ActionListener to be set for the submit button
     */
    @Override
    public void setSubmitAction(ActionListener action) {
        // Remove all existing action listeners
        for (ActionListener al : submitButton.getActionListeners()) {
            submitButton.removeActionListener(al);
        }
        // Add the new action listener
        submitButton.addActionListener(action);
    }
}