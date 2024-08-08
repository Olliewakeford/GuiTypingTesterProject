package GuiTypingTest.OutputProviders;

import GuiTypingTest.RandomSentence;

import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.logging.*;

/**
 * Provides random sentences for the user to type in the typing test.
 * Random sentences are sentences that do not make sense, making them more challenging to type.
 */
public class RandomSentenceProvider implements TextToTypeProvider {
    /**
     * The Words.
     */
    List<String> words;

    /**
     * Constructs a new RandomSentenceProvider.
     * This constructor reads random words from a file and stores them in a list.
     */
    public RandomSentenceProvider() {
        words = new ArrayList<>();

        try {
            //get a list of all words from the RandomWords.txt file
            words = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "/TextResources/RandomWords.txt"));
        } catch (IOException e) {
            Logger.getLogger(NonsenseSentenceProvider.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Returns a list of 40 random sentences.
     *
     * @return a list of 40 random sentences
     */
    // 40 random sentences
    @Override
    public List<String> getTextToType() {
        List<String> paragraph = new ArrayList<>(); //List of sentences to be returned

        for (int i = 0; i < 40; i++) {
            String sentence = RandomSentence.createRandomSentence(words); //create a sentence of random words
            paragraph.add(sentence);
        }

        return paragraph;
    }
}