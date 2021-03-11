import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MarkovTest {
    String filename = "tester.txt";

    /**
     * function that creates a file "tester.txt" with words that will match the words in a HashMap for the tests
     */
    void createTestFile() {
        File f = new File(filename);

        try {
            if(f.createNewFile()) {
                System.out.println(filename + " created successfully.");
            } else {
                System.out.println(filename + " already exists.");
            }
            FileWriter writer = new FileWriter(filename);
            writer.write("Hello World.");
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("Problem when making file..." + e);
            e.printStackTrace();
        }
    }

    @Test
    void testConstructor() {
        Markov markov = null;
        assertNull(markov);
        markov = new Markov();
        assertNotNull(markov);
    }

    @Test
    void testGetWords() {
        Markov markov = new Markov();
        HashMap<String, ArrayList<String>> words = new HashMap<>();

        createTestFile();

        markov.addFromFile(filename);
        assertNotEquals(words, markov.getWords());
        words.put("Hello", new ArrayList<>());
        words.get("Hello").add("World.");
        words.put("__$", new ArrayList<>());
        words.get("__$").add("Hello");
        assertEquals(words, markov.getWords());
    }

    @Test
    void testGetSentence() {
        Markov markov = new Markov();
        String sentence = "";

        createTestFile();

        markov.addFromFile(filename);
        assertNotEquals(sentence, markov.getSentence());
        sentence = "Hello World.";
        assertEquals(sentence, markov.getSentence());
    }

    @Test
    void testRandomWord() {
        Markov markov = new Markov();

        createTestFile();

        markov.addFromFile(filename);
        assertNotEquals(markov.randomWord("__$"), markov.randomWord("Hello"));
    }

    @Test
    void testEndsWithPunctuation() {
        assertTrue(Markov.endsWithPunctuation("Hello."));
        assertFalse(Markov.endsWithPunctuation("Hello"));
    }

    @Test
    void testToString() {
        Markov markov = new Markov();
        HashMap<String, ArrayList<String>> words = new HashMap<>();

        createTestFile();

        markov.addFromFile(filename);
        assertNotEquals(words.toString().length(), markov.toString().length());
        words.put("Hello", new ArrayList<>());
        words.get("Hello").add("World.");
        words.put("__$", new ArrayList<>());
        words.get("__$").add("Hello");
        assertEquals(words.toString().length(), markov.toString().length());
    }
}