package ch.fhnw.edu.efalg.token;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// TODO Document Tokeniser
public final class Tokeniser {
    private final Set<String> keywords;
    private final Set<String> separators;
    private final Pattern separatorPattern;

    public Tokeniser(final Collection<String> keywords, final Collection<Character> separators) {
        this.keywords = new HashSet<>(keywords);
        this.separators = separators.stream().map(Object::toString).collect(Collectors.toSet());
        this.separatorPattern = createSeparatorPattern(separators);
    }

    private static Pattern createSeparatorPattern(final Collection<Character> separators) {
        final var sb = new StringBuilder("\\s+|\n");
        for(char c : separators) {
            sb.append("|(?<=\\").append(c).append(")");
            sb.append("|(?=\\").append(c).append(")");
        }
        return Pattern.compile(sb.toString());
    }

    public List<Token> tokenise(final String s) {
        final String sWithoutComments = removeCommentsAndStrings(s);
        final String[] tokenStrings = separatorPattern.split(sWithoutComments);
        return toTokenList(tokenStrings);
    }

    public List<TokenType> tokeniseEnum(final String s) {
        final String sWithoutComments = removeCommentsAndStrings(s);
        final String[] tokenStrings = separatorPattern.split(sWithoutComments);
        return toTokenEnumList(tokenStrings);
    }

    private enum Reading {
        StringLiteral, LineComment, BlockComment, Other
    }

    private static String removeCommentsAndStrings(final String s) {
        // Remove comments and replace string literals
        // TODO Maybe also replace char literals?
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

    private List<TokenType> toTokenEnumList(String[] tokenStrings) {
        final var tokens = new ArrayList<TokenType>();
        for(var tokenString : tokenStrings) {
            if(separators.contains(tokenString)) {
                tokens.add(TokenType.Separator);
            } else if(keywords.contains(tokenString)) {
                tokens.add(TokenType.Keyword);
            } else if(!"".equals(tokenString)) {
                tokens.add(TokenType.Identifier);
            }
        }
        return tokens;

    }

    private List<Token> toTokenList(String[] tokenStrings) {
        final var tokens = new ArrayList<Token>();
        for(var tokenString : tokenStrings) {
            if(separators.contains(tokenString)) {
                tokens.add(new Separator(tokenString));
            } else if(keywords.contains(tokenString)) {
                tokens.add(new Keyword(tokenString));
            } else if(!"".equals(tokenString)) {
                tokens.add(new Identifier(tokenString));
            }
        }
        return tokens;
    }
}
