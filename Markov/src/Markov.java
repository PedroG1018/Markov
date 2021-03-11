/**
 * Title: Markov.java
 * Abstract: Program reads a text file and creates new random text using the words that were read
 * Author: Pedro Gutierrez Jr.
 * Date: March 1, 2020
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Markov {
    //Constant fields
    private static final String PUNCTUATION = "__$";
    private static final String PUNCTUATION_MARKS = ".?!";
    //Members
    private HashMap<String, ArrayList<String>> words = new HashMap<>();
    private String prevWord;

    /** default/no parameter constructor
     *
     * initializes HashMap "words" with key PUNCTUATION and a new ArrayList as the value
     * sets prevWord to PUNCTUATION
     *
     */
    public Markov() {
        words.put(PUNCTUATION, new ArrayList<>());
        prevWord = PUNCTUATION;
    }

    //getter that returns the list of words in the HashMap
    HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    //method opens the file, calls addLine method to parse the lines from the file individually
    public void addFromFile(String filename) {
        File f = new File(filename);
        Scanner fileScan = null;

        try {
            fileScan = new Scanner(f);
            while (fileScan != null && fileScan.hasNext()) {
                addLine(fileScan.nextLine());
            }
        } catch(FileNotFoundException e) {
            System.out.println("could not find the file " + e);
        }
    }

    //method splits lines from the file into individual words, then passes them into the addWords method
    void addLine(String fileLine) {
        if(fileLine.length() != 0) {
            String[] wordList = fileLine.split(" ");
            for(int i = 0; i < wordList.length; i++) {
                addWord(wordList[i]);
            }
        }
    }

    //method adds words to the HashMap, uses endsWithPunctuation method to ensure that the words are added correctly according to Markov rules
    void addWord(String currentWord) {
        if(endsWithPunctuation(prevWord)) {
            words.get(PUNCTUATION).add(currentWord);
        } else {
            if(!words.containsKey(prevWord)) {
                words.put(prevWord, new ArrayList<>());
            }
            words.get(prevWord).add(currentWord);
        }
        prevWord = currentWord;
    }

    //method builds a random sentence from HashMap contents and returns it, uses randomWord method
    public String getSentence() {
        String sentence = "";
        String currentWord = "";
        currentWord = randomWord(PUNCTUATION);

        while(!endsWithPunctuation(currentWord)) {
            sentence += currentWord + " ";
            currentWord = randomWord(currentWord);
        }
        sentence += currentWord;

        return sentence;
    }

    //method chooses a random word from the HashMap, uses parameter as key to know which ArrayList to randomly select words from
    String randomWord(String randWord) {
        Random rand = new Random();
        int max = words.get(randWord).size();
        int randNum = rand.nextInt(max);
        randWord = words.get(randWord).get(randNum);
        return randWord;
    }

    //method returns true if passed in word ends in punctuation, else returns false
    public static boolean endsWithPunctuation(String puncWord) {
        try {
            for(int i = 0; i < PUNCTUATION_MARKS.length(); i++){
                if(puncWord.charAt(puncWord.length()-1) == PUNCTUATION_MARKS.charAt(i)) {
                    return true;
                }
            }
        } catch(Exception e) {
            System.out.println(puncWord.substring(0, puncWord.length()-1) + " caused an error.");
        }
        return false;
    }

    //returns the toString of the HashMap "words"
    @Override
    public String toString() {
        return words.toString();
    }
}
