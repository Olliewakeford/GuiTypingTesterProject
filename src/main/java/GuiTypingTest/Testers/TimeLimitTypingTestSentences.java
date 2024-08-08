package GuiTypingTest.Testers;

import GuiTypingTest.OutputProviders.*;
import GuiTypingTest.InputProviders.*;
import GuiTypingTest.*;


import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeLimitTypingTestSentences is a typing test that measures typing speed and accuracy
 * for sentences within a specified time limit.
 */
public class TimeLimitTypingTestSentences implements TypingTester {
    private final InputProvider inputProvider;
    private final List<String> textToType;
    private final long timeLimit;
    private long startTime;
    private int correctWords;
    private int totalWords;
    private Result result;
    private final TypingTesterGUI gui;

    /**
     * Constructs a new TimeLimitTypingTestSentences.
     *
     * @param textToTypeProvider the provider for the text to type
     * @param inputProvider      the provider for user input
     * @param timeLimit          the time limit for the test
     * @param gui                the GUI for the typing tester
     */
    public TimeLimitTypingTestSentences(TextToTypeProvider textToTypeProvider, InputProvider inputProvider, long timeLimit, TypingTesterGUI gui) {
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
        displayNextSentence();

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
     * Displays the next sentence to type and sets up the input action.
     */
    private void displayNextSentence() {
        // Check if the time limit is reached or there are no more sentences to display
        if (System.currentTimeMillis() - startTime > timeLimit || textToType.isEmpty()) {
            return;
        }
        String sentence = textToType.removeFirst();

        // Update the display area with the next sentence
        SwingUtilities.invokeLater(() -> {
            JTextArea textArea = ((GUIInputProvider) inputProvider).getDisplayArea();
            textArea.setText(sentence);
        });

        // Set the action to be performed when the submit button is clicked
        inputProvider.setSubmitAction(e -> {
            String userInput = inputProvider.getUserInput();
            totalWords += countWords(sentence);
            correctWords += countCorrectWords(sentence, userInput);
            displayNextSentence();
        });
    }

    /**
     * Counts the number of words in a given text.
     *
     * @param text the text to count words in
     * @return the number of words in the text
     */
    private int countWords(String text) {
        return text.split("\\s+").length;
    }

    /**
     * Counts the number of correct words in the user's input compared to the expected text.
     *
     * @param expected the expected text
     * @param actual the user's input
     * @return the number of correct words
     */
    private int countCorrectWords(String expected, String actual) {
        String[] expectedWords = expected.split("\\s+");
        String[] actualWords = actual.split("\\s+");
        int count = 0;
        for (int i = 0; i < Math.min(expectedWords.length, actualWords.length); i++) {
            if (expectedWords[i].equals(actualWords[i])) {
                count++;
            }
        }
        return count;
    }
}