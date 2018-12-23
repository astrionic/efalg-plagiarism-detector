import ch.fhnw.edu.efalg.PlagiarismDetector;
import ch.fhnw.edu.efalg.InputFileReader;
import ch.fhnw.edu.efalg.Tokeniser;

import java.io.IOException;

public class Main {
    private static final String INPUT_DIRECTORY = "input";
    private static final String KEYWORDS_FILE_PATH = "data\\java_keywords.txt";
    private static final String SEPARATORS_FILE_PATH = "data\\java_separators.txt";

    public static void main(String[] args) {
        try {
            System.out.print(String.format("Reading .java files from directory \"%s\" ... ", INPUT_DIRECTORY));
            final String[] programs = InputFileReader.readProgramFiles(INPUT_DIRECTORY);
            System.out.println(String.format("successfully loaded %d files.", programs.length));

            System.out.print(String.format("Reading keywords from file \"%s\" ... ", KEYWORDS_FILE_PATH));
            final var keywords = InputFileReader.readKeywords(KEYWORDS_FILE_PATH);
            System.out.println(String.format("successfully loaded %d keywords.", keywords.size()));


            System.out.print(String.format("Reading separators from file \"%s\" ... ", SEPARATORS_FILE_PATH));
            final var separators = InputFileReader.readSeparators(SEPARATORS_FILE_PATH);
            System.out.println(String.format("successfully loaded %d separators.\n", separators.size()));

            final var tokeniser = new Tokeniser(keywords, separators);
            final var p = new PlagiarismDetector(tokeniser);
            p.calculateSimilarities(programs);

            System.out.println("Loaded programs:");
            final var programNames = InputFileReader.readProgramNames(INPUT_DIRECTORY);
            for(int i = 0; i < programNames.length; i++) {
                System.out.println(String.format("%2d %s", i, programNames[i]));
            }

            System.out.println("\nSimilarity matrix:");
            System.out.println(p.getSimilarityMatrixAsString());
        } catch(IOException e) {
            System.out.println("File or directory not found.");
            e.printStackTrace();
        }
    }
}
