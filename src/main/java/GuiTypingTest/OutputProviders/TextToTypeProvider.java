package GuiTypingTest.OutputProviders;

import java.util.List;

/**
 * The TextToTypeProvider interface defines the methods required for providing text to type.
 * Implementations of this interface will provide specific text generation logic.
 */
public interface TextToTypeProvider {
    /**
     * Gets the text to be typed by the user.
     * This method should return a list of strings representing the text.
     *
     * @return a list of strings representing the text to type
     */
    List<String> getTextToType();
}