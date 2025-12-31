package ar.com.sodhium.commons.text;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//FUTURE add matching criteria
public class Word {
    @SerializedName("text")
    @Expose
    private String text;

    public Word(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean matches(Word anotherWord) {
        return anotherWord.text.toLowerCase().equals(text.toLowerCase());
    }
    
    public boolean equals(Word anotherWord) {
        return anotherWord.text.equals(text);
    }
}
