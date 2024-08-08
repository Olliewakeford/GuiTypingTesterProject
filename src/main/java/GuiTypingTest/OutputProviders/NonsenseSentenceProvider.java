package GuiTypingTest.OutputProviders;

import GuiTypingTest.RandomSentence;

import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.logging.*;

/**
 * Provides sentences composed of nonsense words for the typing test.
 * Nonsense words are words that are not in the dictionary, making them more challenging to type.
 */
public class NonsenseSentenceProvider implements TextToTypeProvider {
    /**
     * The list of words for the nonsense sentence provider.
     */
    List<String> words;

    /**
     * Constructs a new NonsenseSentenceProvider.
     * This constructor reads nonsense words from a file and stores them in a list.
     */
    public NonsenseSentenceProvider() {
        words = new ArrayList<>();
        try {
            //get a list of all words from the Nonsense.txt file
            words = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "/TextResources/NonsenseWords.txt"));
        } catch (IOException e) {
            Logger.getLogger(NonsenseSentenceProvider.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Returns a list of 40 random sentences composed of nonsense words.
     *
     * @return a list of 40 random sentences composed of nonsense words
     */
    @Override
    public List<String> getTextToType() {
        List<String> paragraph = new ArrayList<>(); //List of sentences to be returned

        //Create 40 random sentences
        for (int i = 0; i < 40; i++) {
            String sentence = RandomSentence.createRandomSentence(words); //create a sentence of random words
            paragraph.add(sentence); //add the sentence to the list of sentences
        }

        return paragraph;
    }
}