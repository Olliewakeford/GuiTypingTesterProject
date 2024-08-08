package GuiTypingTest.InputProviders;

import java.awt.event.ActionListener;

/**
 * InputProvider interface defines the methods required for providing user input.
 */
public interface InputProvider {
    /**
     * Gets the user input from the input source.
     *
     * @return the user input as a String
     */
    String getUserInput();

    /**
     * Sets the action to be performed when the submit action is triggered.
     *
     * @param action the ActionListener to be set for the submit action
     */
    void setSubmitAction(ActionListener action);
}