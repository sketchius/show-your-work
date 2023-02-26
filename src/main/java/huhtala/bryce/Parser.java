package huhtala.bryce;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static void parse (List<Token> tokens) {
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
                    }
            }
        }
    }
}
