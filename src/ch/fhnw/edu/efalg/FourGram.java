package ch.fhnw.edu.efalg;

import ch.fhnw.edu.efalg.token.Token;

import java.util.Objects;

/**
 * Represents a FourGram of {@code Token}s. A FourGram is a contiguous sequence of four items from a text sample.
 */
public class FourGram {
    private final Token one, two, three, four;

    /**
     * Creates a new {@code FourGram} with the given contents.
     *
     * @param one   First item
     * @param two   Second item
     * @param three Third item
     * @param four  Fourth item
     */
    FourGram(Token one, Token two, Token three, Token four) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        FourGram fourGram = (FourGram)o;
        return one == fourGram.one &&
                two == fourGram.two &&
                three == fourGram.three &&
                four == fourGram.four;
    }

    @Override
    public int hashCode() {
        return Objects.hash(one, two, three, four);
    }
}
