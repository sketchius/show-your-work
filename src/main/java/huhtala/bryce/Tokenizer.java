package huhtala.bryce;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static List<Token> parse(String input) {
        int position = 0;
        boolean hasDecimal = false;
        List<Token> tokens = new ArrayList<>();

        String buffer = "";

        while (position < input.length()) {
            char character = input.charAt(position);
            switch (character) {
                case '(':
                case ')':
                    if (!buffer.equals(""))
                        tokens.add(new Token(Token.NUMBER,buffer));
                    tokens.add(new Token(Token.PARENTHESIS,character+""));
                    buffer = "";
                    hasDecimal = false;
                    break;
                case '+':
                case '-':
                case '/':
                case '*':
                    if (!buffer.equals(""))
                       tokens.add(new Token(Token.NUMBER,buffer));
                    tokens.add(new Token(Token.OPERATOR,character+""));
                    buffer = "";
                    hasDecimal = false;
                    break;
                case '.':
                    if (hasDecimal)
                        throw new IllegalArgumentException("Number has two decimal points.");
                    hasDecimal = true;
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    buffer = buffer + character;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid input:  " + character);
            }


            position++;
        }
        if (!buffer.equals(""))
            tokens.add(new Token(Token.NUMBER,buffer));

        return tokens;
    }


}
