package ch.fhnw.edu.efalg;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Calculates similarities between different Java programs.
 */
public final class PlagiarismDetector {
    private final Tokeniser tokeniser;
    private float[][] similarityMatrix;

    /**
     * Creates a new {@link PlagiarismDetector} with the given {@link Tokeniser}
     *
     * @param tokeniser A tokeniser
     */
    public PlagiarismDetector(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }

    /**
     * Calculates the similarities between the given Java programs. The resulting  similarity matrix can be fetched with
     * {@link PlagiarismDetector#getSimilarityMatrix()} or {@link PlagiarismDetector#getSimilarityMatrixAsString()}.
     *
     * @param javaPrograms An array, with each element being a string containing a Java program.
     */
    public void calculateSimilarities(String[] javaPrograms) {
        final var tokenLists = tokenise(javaPrograms);
        final var fourGramLists = tokenLists.stream()
                .map(PlagiarismDetector::toFourGramList)
                .collect(Collectors.toList());
        final var inv = calculateInvertedIndex(fourGramLists);
        similarityMatrix = calculateSimilarityMatrix(inv, fourGramLists.size());
    }

    /**
     * Returns the similarity matrix, if it exists, {@code null} otherwise.
     *
     * @return The similarity matrix, if it exists, {@code null} otherwise
     */
    public float[][] getSimilarityMatrix() {
        if(similarityMatrix == null) return null;
        int n = similarityMatrix.length;
        float[][] copy = new float[n][];
        for(int i = 0; i < n; i++) {
            copy[i] = Arrays.copyOf(similarityMatrix[i], n);
        }
        return copy;
    }

    /**
     * Returns a string representation of the similarity matrix, if it exists, {@code null} otherwise.
     *
     * @return String representation of the similarity matrix, if it exists, {@code null} otherwise
     */
    public String getSimilarityMatrixAsString() {
        if(similarityMatrix == null) return null;
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

    /**
     * Tokenises each program in the given array.
     *
     * @param programs An array with each element being a string of a Java program
     * @return A list, with each element being a list of tokens of the corresponding program
     */
    private List<List<Tokeniser.Token>> tokenise(final String[] programs) {
        final var tokenLists = new ArrayList<List<Tokeniser.Token>>(programs.length);
        for(var program : programs) {
            tokenLists.add(tokeniser.tokenise(program));
        }
        return tokenLists;
    }

    /**
     * Transforms a list of tokens into the corresponding list of 4-grams
     *
     * @param tokens A list of tokens
     * @return The list of 4-grams corresponding to the given list of tokens
     */
    private static List<FourGram> toFourGramList(final List<Tokeniser.Token> tokens) {
        final var fourGrams = new ArrayList<FourGram>();
        for(int i = 0; i + 3 < tokens.size(); i++) {
            fourGrams.add(new FourGram(tokens.get(i), tokens.get(i + 1), tokens.get(i + 2), tokens.get(i + 3)));
        }
        return fourGrams;
    }

    /**
     * Calculates the inverted indices for the given lists of 4-grams.
     *
     * @param fourGramLists A list containing lists of 4-grams, each representing a Java program
     * @return A map containing the index of every program in which a specific 4-gram appears
     */
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

    /**
     * Calculates the similarity matrix for a given map of inverted indices
     *
     * @param invertedIndices Inverted indices
     * @param n               Number of programs
     * @return The resulting similarity matrix
     */
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
