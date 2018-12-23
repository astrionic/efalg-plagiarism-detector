import ch.fhnw.edu.efalg.PlagiarismDetector;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            var p = new PlagiarismDetector();
            float[][] x = p.calculateSimilarities();
            for(int j = 0; j < x[0].length; j++) {
                System.out.print(String.format("%7d", j));
            }
            System.out.println();
            for(int i = 0; i < x.length; i++) {
                System.out.print(String.format("%2d", i));
                for(int j = 0; j < x[i].length; j++) {
                    System.out.print(String.format("%7.3f", x[i][j]));
                }
                System.out.println();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
