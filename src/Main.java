import ch.fhnw.edu.efalg.PlagiarismDetector;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            var p = new PlagiarismDetector();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
