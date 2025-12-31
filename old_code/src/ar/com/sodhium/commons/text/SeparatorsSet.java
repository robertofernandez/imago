package ar.com.sodhium.commons.text;

import java.util.Collection;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeparatorsSet {
    private static final SeparatorsSet defaultSeparatorsSet;

    @SerializedName("separators-map")
    @Expose
    private HashMap<Character, String> separators;

    private boolean includeSpaceChars;

    static {
        Character[] defaultSeparators = {',', ';', ':', '-', '(', ')', '{', '}', '[', ']'};
        defaultSeparatorsSet = new SeparatorsSet(defaultSeparators, true);
    }
    
    public static SeparatorsSet getDefaultseparatorsset() {
        return defaultSeparatorsSet;
    }

    public SeparatorsSet() {
        separators = new HashMap<>();
    }

    public SeparatorsSet(Character[] characters, boolean includeSpaceChars) {
        this.includeSpaceChars = includeSpaceChars;
        separators = new HashMap<>();
        addSeparators(characters);
    }

    public void addSeparator(Character character) {
        separators.put(character, character.toString());
    }

    public void addSeparators(Character[] characters) {
        for (Character character : characters) {
            addSeparator(character);
        }
    }

    public void addSeparators(Collection<Character> characters) {
        for (Character character : characters) {
            addSeparator(character);
        }
    }
    
    public boolean isSeparator(Character character) {
        if(includeSpaceChars) {
            if(Character.isSpaceChar(character)) {
                return true;
            }
        }
        return separators.containsKey(character);
    }
}
