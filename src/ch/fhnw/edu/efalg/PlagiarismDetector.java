package ch.fhnw.edu.efalg;

import ch.fhnw.edu.efalg.token.TokenType;
import ch.fhnw.edu.efalg.token.Tokeniser;

import java.util.*;
import java.util.stream.Collectors;

public final class PlagiarismDetector {
    private final Tokeniser tokeniser;
    private float[][] similarityMatrix;

    public PlagiarismDetector(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }

    public void calculateSimilarities(String[] javaPrograms) {
        final var tokenLists = tokenise(javaPrograms);
        final var fourGramLists = tokenLists.stream().map(this::toFourGramList).collect(Collectors.toList());
        final var inv = calculateInvertedIndex(fourGramLists);
        similarityMatrix = calculateSimilarityMatrix(inv, fourGramLists.size());
    }

    public float[][] getSimilarityMatrix() {
        int n = similarityMatrix.length;
        float[][] copy = new float[n][];
        for(int i = 0; i < n; i++) {
            copy[i] = Arrays.copyOf(similarityMatrix[i], n);
        }
        return copy;
    }

    public String getSimilarityMatrixAsString() {
        var sb = new StringBuilder();
        for(int j = 0; j < similarityMatrix[0].length; j++) {
            sb.append(String.format("%7d", j));
        }
        sb.append("\n");
        for(int i = 0; i < similarityMatrix.length; i++) {
            sb.append(String.format("%2d", i));
            for(int j = 0; j < similarityMatrix[i].length; j++) {
                sb.append(String.format("%7.3f", similarityMatrix[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private List<List<TokenType>> tokenise(final String[] programs) {
        final var tokenLists = new ArrayList<List<TokenType>>(programs.length);
        for(var program : programs) {
            tokenLists.add(tokeniser.tokeniseEnum(program));
        }
        return tokenLists;
    }

    private List<FourGram> toFourGramList(final List<TokenType> tokens) {
        final var fourGrams = new ArrayList<FourGram>();
        for(int i = 0; i + 3 < tokens.size(); i++) {
            fourGrams.add(new FourGram(tokens.get(i), tokens.get(i + 1), tokens.get(i + 2), tokens.get(i + 3)));
        }
        return fourGrams;
    }

    private static Map<FourGram, Set<Integer>> calculateInvertedIndex(final List<List<FourGram>> fourGramLists) {
        final var invertedIndices = new HashMap<FourGram, Set<Integer>>();
        for(int i = 0; i < fourGramLists.size(); i++) {
            for(final var fourGram : fourGramLists.get(i)) {
                if(!invertedIndices.containsKey(fourGram)) {
                    invertedIndices.put(fourGram, new HashSet<>());
                }
                invertedIndices.get(fourGram).add(i);
            }
        }
        return invertedIndices;
    }

    private static float[][] calculateSimilarityMatrix(final Map<FourGram, Set<Integer>> invertedIndices, final int n) {
        final var similarityMatrix = new float[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int a = 0, b = 0, ab = 0;
                for(final Set<Integer> x : invertedIndices.values()) {
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
