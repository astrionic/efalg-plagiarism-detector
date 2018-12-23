package ch.fhnw.edu.efalg;

import ch.fhnw.edu.efalg.token.TokenType;
import ch.fhnw.edu.efalg.token.Tokeniser;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class PlagiarismDetector {
    private final Tokeniser tokeniser;

    public PlagiarismDetector() throws IOException {
        final var keywords = Reader.readJavaKeywords();
        final var separators = Reader.readJavaSeparators();
        this.tokeniser = new Tokeniser(keywords, separators);
    }

    public float[][] calculateSimilarities() throws IOException {
        final var programs = Reader.readJavaFiles("input");
        final var tokenLists = tokenise(programs);
        final var fourGramLists = tokenLists.stream().map(this::toFourGramList).collect(Collectors.toList());
        final var inv = calculateInvertedIndex(fourGramLists);
        return calculateSimilarityMatrix(inv, fourGramLists.size());
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
    
    private static Map<FourGram, Set<Integer>> calculateInvertedIndex(List<List<FourGram>> fourGramLists) {
        final var invertedIndices = new HashMap<FourGram, Set<Integer>>();
        for(int i = 0; i < fourGramLists.size(); i++) {
            for(var fourGram : fourGramLists.get(i)) {
                if(!invertedIndices.containsKey(fourGram)) {
                    invertedIndices.put(fourGram, new HashSet<>());
                }
                invertedIndices.get(fourGram).add(i);
            }
        }
        return invertedIndices;
    }

    private static float[][] calculateSimilarityMatrix(Map<FourGram, Set<Integer>> invertedIndices, int n) {
        var similarityMatrix = new float[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int a = 0, b = 0, ab = 0;
                for(Set<Integer> x : invertedIndices.values()) {
                    if(x.contains(i) && x.contains(j)) ab++;
                    else if(x.contains(i)) a++;
                    else if(x.contains(j)) b++;
                }
                similarityMatrix[i][j] = (float)ab / (a + b + ab);
            }
        }
        return similarityMatrix;
    }
}
