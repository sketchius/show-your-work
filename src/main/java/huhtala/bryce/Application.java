package huhtala.bryce;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a mathematical expression: ");
        String input = scanner.nextLine();
        List<Token> tokens = Tokenizer.parse(input);
        Token result = Parser.parse(tokens);
        int originalLength = result.toString().length();
        print(result.toString(),originalLength);
        while ((result.type != Token.NUMBER)) {
            result.step();
            print(result.toString(),originalLength);
        }
    }

    public static void print(String string, int length) {
        int centeringSpaces = (length - string.length())/2;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < centeringSpaces; i++) {
            stringBuilder.append(" ");
        }
        System.out.println(stringBuilder + string);
    }
}
