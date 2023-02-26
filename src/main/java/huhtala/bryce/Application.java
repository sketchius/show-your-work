package huhtala.bryce;


import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Token> tokens = Tokenizer.parse("(4.00+42424)");

        for (Token token : tokens) {
            System.out.println(token.data);
        }
    }
}
