package GuiTypingTest;

/**
 * Record to store the result of a typing test.
 * It includes the test time, the number of correct words, and the total number of words.
 *
 * @param testTime     the time taken for the test
 * @param correctWords the number of words typed correctly
 * @param totalWords   the total number of words typed
 */
public record Result(long testTime, int correctWords, int totalWords) {
    /**
     * Method to calculate the speed of the user as words per minute.
     *
     * @return the speed of the user in words per minute
     */
    public int calculateSpeed() {
        int seconds = (int) Math.floor((double) testTime / 1000);
        return (int) ((double) correctWords / seconds * 60);
    }

    /**
     * Method to calculate the percentage of words spelt perfectly.
     *
     * @return the accuracy of the user in percentage
     */
    public int calculateAccuracy() {
        return (int) ((double) correctWords / totalWords * 100);
    }

    /**
     * Method to display the result of the test.
     *
     * @return a string representation of the test result
     */
    @Override
    public String toString() {
        double testTimeInMinutes = Math.round(testTime / 60000.0 * 100.0) / 100.0;
        int speed = calculateSpeed();
        int accuracy = calculateAccuracy();

        return "Test Duration: " + testTimeInMinutes + " minutes\n" +
                "Total Words: " + totalWords + "\n" +
                "Correct Words: " + correctWords + "\n" +
                "Speed: " + speed + " words per minute\n" +
                "Accuracy: " + accuracy + "%\n";
    }
}