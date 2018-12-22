package ch.fhnw.edu.efalg.token;

public class Identifier extends Token {
    public final String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Identifier(\"" + identifier + "\")";
    }
}
