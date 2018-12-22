package ch.fhnw.edu.efalg.token;

public class Separator extends Token {
    public final String separator;

    public Separator(String separator) {
        this.separator = separator;
    }

    @Override
    public String toString() {
        return "Separator(\"" + separator + "\")";
    }
}
