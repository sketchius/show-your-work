package huhtala.bryce;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static Token parse (List<Token> tokens) {
        int parentheticalLevel = 0;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            switch (token.type) {
                case Token.PARENTHESIS:
                    switch (token.data) {
                        case "(":
                            parentheticalLevel++;
                            break;
                        case ")":
                            if (parentheticalLevel == 0)
                                throw new IllegalArgumentException("Found ')' without matching '('.");

                            parentheticalLevel--;
                            break;
                        default:
                            throw new IllegalArgumentException("Internal Error: Expecting parenthesis.");
                    }
                    break;
                case Token.OPERATOR:
                    token.setParentheticalLevel(parentheticalLevel);
                    break;
            }
        }

        while (true) {
            int highestPriorityOperatorIndex = -1;
            int highestPriorityOperatorScore = -1;

            for (int i = 0; i < tokens.size(); i++) {

                Token token = tokens.get(i);
                if (token.type == Token.OPERATOR && token.getScore() > highestPriorityOperatorScore) {
                    highestPriorityOperatorIndex = i;
                    highestPriorityOperatorScore = token.getScore();
                }
            }

            if (highestPriorityOperatorIndex != -1) {
                int i = highestPriorityOperatorIndex;
                if (i == 0) throw new IndexOutOfBoundsException("Operator with index 0.");
                if (i == tokens.size()-1) throw new IndexOutOfBoundsException("Operator with index " + (tokens.size()-1));
                boolean showParenthesis = false;
                if (i >= 2 && tokens.get(i-2).data.equals("(") && i < tokens.size()-2 && tokens.get(i+2).data.equals(")"))
                    showParenthesis = true;
                Token leftOperand = tokens.get(i-1);
                Token operator = tokens.get(i);
                Token rightOperand = tokens.get(i+1);
                Token expression = new Token(new Token[] {leftOperand, operator, rightOperand}, showParenthesis);
                tokens.remove(i+1);
                tokens.remove(i-1);
                if (showParenthesis) {
                    tokens.remove(i-2);
                    tokens.remove(i-1);
                    tokens.set(i-2,expression);
                } else
                    tokens.set(i-1,expression);
            } else break;
        }
        return tokens.get(0);
    }
}
