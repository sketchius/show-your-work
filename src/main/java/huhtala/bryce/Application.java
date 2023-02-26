package huhtala.bryce;


import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Token> tokens = Tokenizer.parse("10+(4*(32/8))/4");
        Token result = Parser.parse(tokens);
        result.print();
        System.out.println();
        while ((result.type != Token.NUMBER)) {
            result.step();
            result.print();
            System.out.println();
        }
    }
}
