import ch.fhnw.edu.efalg.Reader;
import ch.fhnw.edu.efalg.token.TokenType;
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

            Tokeniser tokeniser = new Tokeniser(keywords, separators);
            var tokens = tokeniser.tokeniseEnum(strings[0]);
            for(var t : tokens) {
                System.out.println(t.toString());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
