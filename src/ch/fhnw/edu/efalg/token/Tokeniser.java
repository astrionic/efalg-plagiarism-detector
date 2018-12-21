package ch.fhnw.edu.efalg.token;

import java.util.ArrayList;
import java.util.List;

// TODO Document Tokeniser
public final class Tokeniser {
    private final List<String> keywords;
    private final List<String> separators;

    public Tokeniser(List<String> keywords, List<String> separators) {
        this.keywords = new ArrayList<>(keywords);
        this.separators = new ArrayList<>(separators);
    }

    public List<Token> tokenise(final String s) {
        List<Token> tokens = new ArrayList<>();
        var lines = s.split("\n");
        // TODO Read lines, filter comments, correctly parse strings
        return tokens;
    }
}
