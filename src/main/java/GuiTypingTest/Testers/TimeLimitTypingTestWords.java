package GuiTypingTest.Testers;

import GuiTypingTest.InputProviders.InputProvider;
import GuiTypingTest.InputProviders.GUIInputProvider;
import GuiTypingTest.OutputProviders.TextToTypeProvider;
import GuiTypingTest.Result;
import GuiTypingTest.TypingTesterGUI;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeLimitTypingTestWords is a typing test that measures typing speed and accuracy
 * for individual words within a specified time limit.
 */
public class TimeLimitTypingTestWords implements TypingTester {
    private final InputProvider inputProvider;
    private final List<String> textToType;
    private final long timeLimit;
    private long startTime;
    private int correctWords;
    private int totalWords;
    private Result result;
    private final TypingTesterGUI gui;

    /**
     * Constructs a new TimeLimitTypingTestWords.
     *
     * @param textToTypeProvider the provider for the text to type
     * @param inputProvider      the provider for user input
     * @param timeLimit          the time limit for the test
     * @param gui                the GUI for the typing tester
     */
    public TimeLimitTypingTestWords(TextToTypeProvider textToTypeProvider, InputProvider inputProvider, long timeLimit, TypingTesterGUI gui) {
        this.inputProvider = inputProvider;
        this.timeLimit = timeLimit;
        this.textToType = textToTypeProvider.getTextToType();
        this.gui = gui;
    }

    /**
     * Starts the typing test and sets up the timer.
     */
    @Override
    public void startTest() {
        startTime = System.currentTimeMillis();
        displayNextWord();

        // Schedule a task to complete the test when the time limit is reached
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    result = completeTest();
                    gui.completeTest();
                });
            }
        }, timeLimit);

    }

    /**
     * Completes the typing test and calculates the result.
     *
     * @return the result of the typing test
     */
    @Override
    public Result completeTest() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        result = new Result(elapsedTime, correctWords, totalWords);
        return result;
    }

    /**
     * Gets the start time of the test.
     *
     * @return the start time in milliseconds since epoch
     */
    @Override
    public long getStartTime() {
        return startTime;
    }

    /**
     * Displays the next word to type and sets up the input action.
     */
    private void displayNextWord() {
        // Check if the time limit is reached or there are no more words to display
        if (System.currentTimeMillis() - startTime > timeLimit || textToType.isEmpty()) {
            return;
        }
        String word = textToType.removeFirst();

        // Update the display area with the next word
        SwingUtilities.invokeLater(() -> {
            JTextArea textArea = ((GUIInputProvider) inputProvider).getDisplayArea();
            textArea.setText(word);
        });

        // Set the action to be performed when the submit button is clicked
        inputProvider.setSubmitAction(e -> {
            String userInput = inputProvider.getUserInput();
            totalWords++; // Increment totalWords by 1 for each word processed
            if (word.equals(userInput)) {
                correctWords++;
            }
            displayNextWord();
        });
    }
}