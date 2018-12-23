import ch.fhnw.edu.efalg.PlagiarismDetector;
import ch.fhnw.edu.efalg.Reader;
import ch.fhnw.edu.efalg.token.Tokeniser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            final String[] programs = Reader.readJavaFiles("input");
            final var keywords = Reader.readJavaKeywords();
            final var separators = Reader.readJavaSeparators();
            final var tokeniser = new Tokeniser(keywords, separators);
            final var p = new PlagiarismDetector(tokeniser);
            p.calculateSimilarities(programs);
            System.out.println(p.getSimilarityMatrixAsString());
        } catch(IOException e) {
            System.out.println("File or directory not found.");
            e.printStackTrace();
        }
    }
}
