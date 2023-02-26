package huhtala.bryce;

public class Token {
    int type;
    String data;

    public static final int OPERATOR = 0;
    public static final int NUMBER = 1;
    public static final int PARENTHESIS = 2;

    public Token(int type, String data) {
        this.type = type;
        this.data = data;
    }
}
