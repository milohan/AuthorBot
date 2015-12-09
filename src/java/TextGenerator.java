
/**
 * Class to artificially generate sentences
 *
 * Fix these comments!!
 *
 * @author NAME GOES HERE!!
 *
 * @author Sean Zhu
 * @version 7/15 - added toString() to print freq list
 */
import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import structure5.Association;

public class TextGenerator {

    // List of Association of letter pairs and frequency lists
    protected List<Association<StringPair, FreqList>> letPairList;

    // Random number generator
    Random rng;
    StringBuilder sb;
    String author;
    String url;

    // Default constructor
    public TextGenerator() {

    }

    public String getAuthor() {
//        int i = 1;
//        while(i != 0 ) {
        findNew();
       
        return author;
    }

    public String getText() {
        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public void findNew() {
        WordStream ws = new WordStream();
        // Display the dialog box and make sure the user did not cancel.
        // Find out which file the user selected.
        int numLine = 0;

        try {
            // Open the file.
            Random r = new Random();
            int next = r.nextInt(21) + 1;
//            url = "http://www.gutenberg.org/files/" + next + "/" + next + ".txt";
            // StringBuffer buildpage = new StringBuffer();

            // try {
            // BufferedInputStream page =
            // new BufferedInputStream(new URL(url).openStream());
            //
            // for (int input = page.read(); input != -1; input =
            // page.read()) {
            // buildpage.append((char) input);
            // }
            //
            // } catch (Exception ex) {
            // System.out.println(ex);
            // }
            // Open the file.
//            BufferedReader input = new BufferedReader(
//                    new InputStreamReader(new URL(url).openStream()));
            BufferedReader input = new BufferedReader(new FileReader("/Users/Sean/Desktop/Books/"+next+".txt"));
            // Fill up the editing area with the contents of the file being
            // read.
            author = "Unknown";
            author = input.readLine();
            url = input.readLine();
            String line = input.readLine();
            while (line != null && numLine <= 1000) {
                if (numLine <= 400) {
                    line = input.readLine(); // ignore the line
                } else {
                    System.out.println(line);
                    ws.addLexItems(line);
                    // System.out.println(line);
                    line = input.readLine();
                }
                numLine++;
            }
            // System.out.println("Finished reading data");
            // Close the file
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (numLine <= 0) {
//            return 1;
//        }
        System.out.println("Finished entering words in wordstream");

        letPairList = new ArrayList<Association<StringPair, FreqList>>();
        rng = new Random();
        // Iterate through the wordstream tabulating the trigrams
        String word1 = "", word2 = ws.nextToken(), word3 = ws.nextToken();
        while (ws.hasMoreTokens()) {
            word1 = word2;
            word2 = word3;
            word3 = ws.nextToken();
            enter(word1, word2, word3);
        }
//        System.out.println(letPairList);
        System.out.println("Finished entering words in trigrams");
        int textLength = 400;
        StringPair begWords = randomPair();

        word1 = begWords.getFirst();
        word2 = begWords.getSecond();
        word3 = "";

        sb = new StringBuilder();
        sb.append(word1);
        sb.append(" " + word2);
//        System.out.print(word1 + " ");
//        System.out.print(word2 + " ");
        for (int i = 0; i < textLength; ++i) {
            word3 = getNextWord(word1, word2);
            if (word3.equals("")) {
                word1 = begWords.getFirst();
                word2 = begWords.getSecond();
                word3 = getNextWord(word1, word2);
                // might be necessary
//                if (isEndChar(word3.charAt(0))) {
//                    sb.append(word3);
//                } else {
//                    sb.append(" "+ word3);
//                }
//                word1 = word2;
//                word2 = word3;
            }
//            System.out.print(word3 + " ");
            if (isEndChar(word3.charAt(0))) {
                sb.append(word3);
            } else {
                sb.append(" " + word3);
            }
            word1 = word2;
            word2 = word3;
//            if (i % 20 == 0) {
//                System.out.println();
//            }
        }

        // insert code to print table & generate new text using
        // random number generator
//        System.out.println();
//        System.out.println(toString());
//
//        System.out.println("yo");
//        return 0;
    }

    private boolean isEndChar(char c) {
        String endChars = ";,.:?!'-";
        return (endChars.contains(c + ""));
    }

    public StringPair randomPair() {
        Association<StringPair, FreqList> chosen = letPairList.get(0);
        return (chosen.getKey());
    }

    /**
     * @param pair a pair of words
     * @return the index of the pair in the list of trigrams or -1 if the pair
     * does not occur
     */
    private int indexOf(StringPair pair) {
        int index = letPairList.indexOf(new Association<StringPair, FreqList>(
                pair, new FreqList()));
        return (index);
    }

    /**
     * Records the trigram <first, second, third>
     */
    public void enter(String first, String second, String third) {
        StringPair newPair = new StringPair(first, second);
        int index = indexOf(newPair);
        if (index == -1) {
            // Add the pair of words (along with a new frequency list containing
            // the third word) to the list of trigrams
            FreqList freqList = new FreqList();
            freqList.add(third);
            letPairList.add(new Association<StringPair, FreqList>(newPair,
                    freqList));
        } else {
            Association<StringPair, FreqList> assoc = letPairList.get(index);
            (assoc.getValue()).add(third);
        }
    }

    public String getString() {
        return sb.toString();
    }

    public String toString() {
        String str = "";
        System.out.println("Table size is " + letPairList.size());
        for (Association<StringPair, FreqList> pairs : letPairList) {
            System.out.println(pairs.toString());
        }
        return str;
    }

    /**
     * Given a pair of words <first, second> returns a randomly selected third
     * word
     *
     * @param first first word
     * @param second second word
     * @return randomly selected word
     */
    public String getNextWord(String first, String second) {
        int index = indexOf(new StringPair(first, second));
        String returnedWord = "";
        if (index != -1) {
            Association<StringPair, FreqList> assoc = letPairList.get(index);
            returnedWord = (assoc.getValue()).get(rng.nextDouble());
        }
        return returnedWord;
    }

    // START OF CODE FOR MAIN PROGRAM -- WRITE & FIX COMMENTS
    public static void main(String args[]) {

        try {
            new FileReader("if.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
