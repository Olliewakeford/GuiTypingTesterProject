package GuiTypingTest.Testers;

import GuiTypingTest.Result;

/**
 * The TypingTester interface defines the methods required for a typing test.
 * Implementations of this interface will provide specific typing test logic.
 */
public interface TypingTester {
    /**
     * Starts the typing test.
     * This method should initialize the test and begin tracking time and user input.
     */
    void startTest();

    /**
     * Completes the typing test.
     * This method should calculate the final result based on the user's performance.
     *
     * @return the result of the typing test
     */
    Result completeTest();

    /**
     * Gets the start time of the typing test.
     *
     * @return the start time in milliseconds since epoch
     */
    long getStartTime();
}