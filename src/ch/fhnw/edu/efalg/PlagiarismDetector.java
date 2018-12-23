package ch.fhnw.edu.efalg;

import ch.fhnw.edu.efalg.token.TokenType;
import ch.fhnw.edu.efalg.token.Tokeniser;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PlagiarismDetector {
    private final Tokeniser tokeniser;

    public PlagiarismDetector() throws IOException {
        final var keywords = Reader.readJavaKeywords();
        final var separators = Reader.readJavaSeparators();
        this.tokeniser = new Tokeniser(keywords, separators);
    }

    public void calculateSimilarities() throws IOException {
        final var programs = Reader.readJavaFiles("input");
        final var tokenLists = tokenise(programs);
        final List<List<FourGram>> fourGramLists = tokenLists.stream().map(this::toFourGramList).collect(Collectors.toList()); // TODO Array?

        // Inverted index TODO Put into its own method?2
        final var inv = new HashMap<FourGram, Set<Integer>>();
        for(int i = 0; i < fourGramLists.size(); i++) {
            for(var fourGram : fourGramLists.get(i)) {
                if(!inv.containsKey(fourGram)) {
                    inv.put(fourGram, new HashSet<>());
                }
                inv.get(fourGram).add(i);
            }
        }
        // Calculate similarity matrix

        // TODO Finish calculateSimilarities implementation
    }

    private List<List<TokenType>> tokenise(String[] programs) throws IOException {
        final var tokenLists = new ArrayList<List<TokenType>>(programs.length);
        for(var program : programs) {
            tokenLists.add(tokeniser.tokeniseEnum(program));
        }
        return tokenLists;
    }

    private List<FourGram> toFourGramList(List<TokenType> tokens) {
        final var fourGrams = new ArrayList<FourGram>();
        for(int i = 0; i + 3 < tokens.size(); i++) {
            fourGrams.add(new FourGram(tokens.get(i), tokens.get(i + 1), tokens.get(i + 2), tokens.get(i + 3)));
        }
        return fourGrams;
    }

    private List<List<FourGram>> toFourGramLists(List<List<TokenType>> tokenLists) {
        final var fourGramLists = new ArrayList<List<FourGram>>();
        for(List<TokenType> tokenList : tokenLists) {
            fourGramLists.add(toFourGramList(tokenList));
        }
        return fourGramLists;
    }

}
