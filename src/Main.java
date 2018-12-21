import ch.fhnw.edu.efalg.Reader;
import ch.fhnw.edu.efalg.token.Tokeniser;
import ch.fhnw.edu.efalg.token.Token;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String[] strings = Reader.readJavaFiles("input");
            var keywords = Reader.readJavaKeywords();
            var separators = Reader.readJavaSeparators();
            Tokeniser t = new Tokeniser(keywords, separators);
            List<Token> tokens = t.tokenise(strings[0]);
            /*
            for(Token t : tokens) {
                System.out.println("\"" + t.toString() + "\"");
            }
            */
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}
