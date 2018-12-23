package ch.fhnw.edu.efalg;

import ch.fhnw.edu.efalg.token.TokenType;

import java.util.Objects;

public class FourGram {
    private final TokenType one, two, three, four;

    FourGram(TokenType one, TokenType two, TokenType three, TokenType four) {
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
