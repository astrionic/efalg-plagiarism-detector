package ch.fhnw.edu.efalg.token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

// TODO Document Tokeniser
public final class Tokeniser {
    private final List<String> keywords;
    private final Pattern separators;

    public Tokeniser(List<String> keywords, char[] separators) {
        this.keywords = new ArrayList<>(keywords);
        this.separators = createSeparatorPattern(separators);
    }

    private static String removeCommentsAndStrings(final String s) {
        // Remove comments and replace string literals
        // TODO Maybe also replace char literals?
        var chars = s.toCharArray();
        var sb = new StringBuilder();
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

    private Pattern createSeparatorPattern(char[] separators) {
        var sb = new StringBuilder("\\s+|\n");
        for(char c : separators) {
            sb.append("|(?<=\\").append(c).append(")");
            sb.append("|(?=\\").append(c).append(")");
        }
        return Pattern.compile(sb.toString());
    }

    public List<Token> tokenise(final String s) {
        var noComment = removeCommentsAndStrings(s);
        // TODO Tokenise
        var x = separators.split(noComment);
        for(var y : x) {
            System.out.println("Token \"" + y.replaceAll("\n", "") + "\"");
        }
        List<Token> tokens = new ArrayList<>();
        return tokens;
    }

    private enum Reading {
        StringLiteral, LineComment, BlockComment, Other
    }
}
