package ch.fhnw.edu.efalg.token;

public class Keyword extends Token {
    public final String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public String toString() {
        return "Keyword(\"" + keyword + "\")";
    }
}
