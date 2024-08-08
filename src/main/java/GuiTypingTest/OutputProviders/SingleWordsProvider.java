package GuiTypingTest.OutputProviders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.*;

/**
 * Implementation of TextToTypeProvider to provide single words for user to type.
 * Single words are either random words or nonsense words.
 */
public class SingleWordsProvider implements TextToTypeProvider {
    /**
     * The Words.
     */
    List<String> words;


    /**
     * Constructs a new SingleWordsProvider where int wordsType is the type of words to be provided.
     * 0 for random words.
     * 1 for nonsense words.
     *
     * @param wordsType the type of words to be provided
     */
    public SingleWordsProvider(int wordsType) {
        words = new ArrayList<>();

        try {
            // 0 for RandomWords.txt, 1 for NonsenseWords.txt
            if (wordsType == 0) {
                //get a list of all words from the RandomWords.txt file
                words = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "/TextResources/RandomWords.txt"));
            } else if (wordsType == 1) {
                //get a list of all words from the Nonsense.txt file
                words = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "/TextResources/NonsenseWords.txt"));
            }
        } catch (IOException e) {
            Logger.getLogger(NonsenseSentenceProvider.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    /**
     * Returns a list of 1000 words for the user to type.
     *
     * @return a list of words for the user to type
     */
    @Override
    public List<String> getTextToType() {
        Collections.shuffle(words); //shuffle the list of all words

        // Return only the first 1000 words, or the entire list if it has less than 1000 words
        return words.subList(0, Math.min(words.size(), 1000));
    }

}
