package ch.fhnw.edu.efalg;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Handles tokenisation of source code
 */
public final class Tokeniser {
    private final Set<String> keywords;
    private final Set<String> separators;
    private final Pattern separatorPattern;

    /**
     * Creates a new {@link Tokeniser} with the given keywords and separators
     *
     * @param keywords   Keywords
     * @param separators Separators
     */
    public Tokeniser(final Collection<String> keywords, final Collection<Character> separators) {
        this.keywords = new HashSet<>(keywords);
        this.separators = separators.stream().map(Object::toString).collect(Collectors.toSet());
        this.separatorPattern = createSeparatorPattern(separators);
    }

    /**
     * Creates a {@link Pattern} from the given {@link Collection} of separators which can be used to split the string
     * containing the code.
     *
     * @param separators Collection containing the separators
     * @return The pattern
     */
    private static Pattern createSeparatorPattern(final Collection<Character> separators) {
        final var sb = new StringBuilder("\\s+|\n");
        for(char c : separators) {
            sb.append("|(?<=\\").append(c).append(")");
            sb.append("|(?=\\").append(c).append(")");
        }
        return Pattern.compile(sb.toString());
    }

    /**
     * Tokenises a string, creating a list of tokens that represent the content.
     *
     * @param s The string to tokenise
     * @return Generated list of tokens
     */
    public List<Token> tokenise(final String s) {
        final String sWithoutComments = removeCommentsAndStrings(s);
        final String[] tokenStrings = separatorPattern.split(sWithoutComments);
        return toTokenList(tokenStrings);
    }

    /**
     * Removes Java line comments and block comments from the given string and replaces string literals with
     * "StringLiteral"
     *
     * @param s The string
     * @return The string with comments removed and string literals replaced
     */
    private static String removeCommentsAndStrings(final String s) {
        final var chars = s.toCharArray();
        final var sb = new StringBuilder();
        var state = Reading.Other;
        for(int i = 0; i < chars.length; i++) {
            switch(state) {
                case StringLiteral:
                    switch(chars[i]) {
                        case '\\':
                            i++;
                            break;
                        case '"':
                            sb.append("StringLiteral");
                            state = Reading.Other;
                    }
                    break;
                case LineComment:
                    if(chars[i] == '\n') {
                        state = Reading.Other;
                        sb.append(chars[i]);
                    }
                    break;
                case BlockComment:
                    if(chars[i] == '*' && i + 1 < chars.length && chars[i + 1] == '/') {
                        state = Reading.Other;
                        i++;
                    }
                    break;
                default:
                    switch(chars[i]) {
                        case '"':
                            state = Reading.StringLiteral;
                            break;
                        case '/':
                            if(i + 1 < chars.length && chars[i + 1] == '/') {
                                state = Reading.LineComment;
                                i++;
                            } else if(i + 1 < chars.length && chars[i + 1] == '*') {
                                state = Reading.BlockComment;
                                i++;
                            } else {
                                sb.append(chars[i]);
                            }
                            break;
                        default:
                            sb.append(chars[i]);
                    }
            }
        }
        return sb.toString();
    }

    /**
     * Represents the states of the state machine used in {@link Tokeniser#removeCommentsAndStrings(String)}.
     */
    private enum Reading {
        StringLiteral, LineComment, BlockComment, Other
    }

    /**
     * Converts an array containing tokens in string form into a list of {@link Token}s.
     *
     * @param tokenStrings Array containing the tokens in string form.
     * @return A list containing the {@link Token} representation of the given strings
     */
    private List<Token> toTokenList(String[] tokenStrings) {
        final var tokens = new ArrayList<Token>();
        for(var tokenString : tokenStrings) {
            if(separators.contains(tokenString)) {
                tokens.add(Token.Separator);
            } else if(keywords.contains(tokenString)) {
                tokens.add(Token.Keyword);
            } else if(!"".equals(tokenString)) {
                tokens.add(Token.Identifier);
            }
        }
        return tokens;

    }
}
