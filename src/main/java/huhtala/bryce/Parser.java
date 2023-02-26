package huhtala.bryce;

public class Parser {
    public static void parse(String input) {
        int position = 0;
        int parentheticalLevel = 0;

        String buffer = "";

        while (position < input.length()) {
            char character = input.charAt(position);
            switch (character) {
                case '(':
                    parentheticalLevel++;
                case ')':
                    if (parentheticalLevel == 0)
                        throw new IllegalArgumentException("Found ')' without matching '('.");
                    parentheticalLevel--;
                case '+':
                case '-':
                case '/':
                case '*':
                    //Tokenize
                    break;
                case 0: case 1: case 2: case 3: case 4:
                case 5: case 6: case 7: case 8: case 9:
                case '.':
                    buffer = buffer + character;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid input:  " + character);
            }
        }
    }


}
