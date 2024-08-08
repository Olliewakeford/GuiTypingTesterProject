package GuiTypingTest;

import java.util.List;
import java.util.Random;

/**
 * Class to generate a random sentence from a list of words.
 */
public class RandomSentence {
    /**
     * Method to create a random sentence from a list of words.
     * The sentence length varies and the first word is capitalized.
     *
     * @param words the list of words to create the sentence from
     * @return a random sentence created from the words list
     */
    public static String createRandomSentence(List<String> words) {
        StringBuilder sentence = new StringBuilder();

        Random rand = new Random();
        int numWordsInSentence = rand.nextInt(5) + 5; //to vary sentence length

        for (int i = 0; i < numWordsInSentence; i++) {
            int index = rand.nextInt(words.size()); //choose a random word from the list of all words

            if (i == 0) {
                sentence.append(words.get(index).substring(0, 1).toUpperCase()).append(words.get(index).substring(1)).append(" "); //capitalize the first word
            } else if (i < numWordsInSentence - 1) {
                sentence.append(words.get(index)).append(" "); //add the word to the sentence
            } else {
                sentence.append(words.get(index)); //add the word to the sentence without a space at the end
            }
        }

        sentence.append(".");
        return sentence.toString();
    }
}