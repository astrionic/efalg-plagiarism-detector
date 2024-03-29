package ch.fhnw.edu.efalg;

import java.util.Objects;

/**
 * Represents a 4-gram of {@link Tokeniser.Token}s. A FourGram is a contiguous sequence of four items from a text sample.
 */
public class FourGram {
    private final Tokeniser.Token one, two, three, four;

    /**
     * Creates a new 4-gram with the given contents.
     *
     * @param one   First item
     * @param two   Second item
     * @param three Third item
     * @param four  Fourth item
     */
    FourGram(Tokeniser.Token one, Tokeniser.Token two, Tokeniser.Token three, Tokeniser.Token four) {
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
