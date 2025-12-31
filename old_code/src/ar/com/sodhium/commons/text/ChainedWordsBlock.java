package ar.com.sodhium.commons.text;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.commons.strings.StringUtils;

/**
 * Sequence of words.
 * 
 * @author Roberto G. Fernandez
 *
 */
public class ChainedWordsBlock {
    @SerializedName("words")
    @Expose
    private ArrayList<Word> words;

    public ChainedWordsBlock(ArrayList<Word> words) {
        this.words = words;
    }

    public ChainedWordsBlock(String input) {
        this(input, SeparatorsSet.getDefaultseparatorsset());
    }

    public ChainedWordsBlock(String input, SeparatorsSet separators) {
        this.words = splitWords(input, separators);
    }

    private ArrayList<Word> splitWords(String input, SeparatorsSet separators) {
        ArrayList<Word> outputSequence = new ArrayList<>();
        String phrase = input.trim().toLowerCase();
        String currentWord = "";
        Integer i = 0;
        for (Character character : phrase.toCharArray()) {
            i++;
            if (!separators.isSeparator(character)) {
                currentWord += character;
            } else {
                if (StringUtils.isNotBlank(currentWord)) {
                    outputSequence.add(new Word(currentWord));
                    currentWord = "";
                }
            }
        }
        if (StringUtils.isNotBlank(currentWord)) {
            outputSequence.add(new Word(currentWord));
        }
        return outputSequence;
    }

    @Override
    public String toString() {
        String output = "|";
        for (Word child : words) {
            output += child.toString() + "|";
        }
        return output;
    }

    /**
     * Returns if input chain contains all items
     * 
     * @param input
     * @return
     */
    public boolean matches(ChainedWordsBlock input) {
        ArrayList<Word> inputList = input.words;
        for (int i = 0; i < inputList.size(); i++) {
            if (matches(inputList, i)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Word> getMatchingWords(ChainedWordsBlock input) {
        ArrayList<Word> inputList = input.words;
        for (int i = 0; i < inputList.size(); i++) {
            ArrayList<Word> matchingWords = getMatchingWords(inputList, i);
            if (matchingWords.size() > 0) {
                return matchingWords;
            }
        }
        return new ArrayList<>();
    }

    private boolean matches(ArrayList<Word> inputList, int initialIndex) {
        if (inputList.size() < initialIndex + words.size()) {
            return false;
        }
        int i = initialIndex;
        for (Word word : words) {
            if (!word.getText().trim().equalsIgnoreCase(inputList.get(i++).getText().trim())) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Word> getMatchingWords(ArrayList<Word> inputList, int initialIndex) {
        ArrayList<Word> output = new ArrayList<>();
        if (inputList.size() < initialIndex + words.size()) {
            return output;
        }
        int i = initialIndex;
        for (Word word : words) {
            Word candidateWord = inputList.get(i++);
            if (!word.getText().trim().equalsIgnoreCase(candidateWord.getText().trim())) {
                return new ArrayList<>();
            } else {
                output.add(candidateWord);
            }
        }
        return output;
    }

    public boolean matchesFullString(ChainedWordsBlock input) {
        ArrayList<Word> inputList = input.words;
        if (inputList.size() != words.size()) {
            return false;
        }
        for (int i = 0; i < inputList.size(); i++) {
            if (!inputList.get(i).getText().equalsIgnoreCase(words.get(i).getText())) {
                return false;
            }
        }
        return true;
    }

    public String getAsString() {
        String output = "";
        String separator = "";
        for (Word child : words) {
            output += separator + child.getText();
            separator = " ";
        }
        return output;
    }

    public void addBlock(ChainedWordsBlock newblock) {
        words.addAll(newblock.getWords());
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public ChainedWordsBlock getSubBlock(int initialIndex, int finalIndex) {
        ArrayList<Word> outputWords = new ArrayList<>();
        for (int i = initialIndex; i < finalIndex; i++) {
            outputWords.add(words.get(i));
        }
        return new ChainedWordsBlock(outputWords);
    }

}
