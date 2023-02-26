package huhtala.bryce;

public class Token {
    int type;
    String data;
    int parentheticalLevel;
    boolean showParenthesis;
    Token[] children;

    public static final int OPERATOR = 0;
    public static final int NUMBER = 1;
    public static final int PARENTHESIS = 2;
    public static final int EXPRESSION = 3;

    public Token(int type, String data) {
        this.type = type;
        this.data = data;
        parentheticalLevel = 0;
    }

    public Token(Token[] children, boolean showParenthesis) {
        this.type = Token.EXPRESSION;
        this.data = "";
        this.children = children;
        parentheticalLevel = 0;
        this.showParenthesis = showParenthesis;
    }

    public boolean isShowParenthesis() {
        return showParenthesis;
    }

    public void setShowParenthesis(boolean showParenthesis) {
        this.showParenthesis = showParenthesis;
    }

    public int getParentheticalLevel() {
        return parentheticalLevel;
    }

    public void setParentheticalLevel(int parentheticalLevel) {
        this.parentheticalLevel = parentheticalLevel;
    }

    public int getScore() {
        if (type != 0) return -1;
        switch (data) {
            case "+":
            case "-":
                return 1 + (parentheticalLevel+1) * 10;
            case "*":
            case "/":
                return 2 + (parentheticalLevel+1) * 10;
            default:
                return -1;
        }
    }

    public void step() {
        if (type == Token.EXPRESSION) {
            if (children[0].type == Token.EXPRESSION) {
                if (children[2].type == Token.NUMBER && children[2].showParenthesis)
                    children[2].showParenthesis = false;
                else
                    children[0].step();
            }
            else if (children[2].type == Token.EXPRESSION) {
                if (children[0].type == Token.NUMBER && children[0].showParenthesis)
                    children[0].showParenthesis = false;
                else
                    children[2].step();
            }
            else {
                if (children[0].type == Token.NUMBER && children[0].showParenthesis)
                    children[0].showParenthesis = false;
                else if (children[2].type == Token.NUMBER && children[2].showParenthesis)
                    children[2].showParenthesis = false;
                else {
                    String value;
                    int operand1 = Integer.valueOf(children[0].data);
                    int operand2 = Integer.valueOf(children[2].data);
                    switch (children[1].data) {
                        case "+":
                            value = (operand1 + operand2) + "";
                            break;
                        case "-":
                            value = (operand1 - operand2) + "";
                            break;
                        case "*":
                            value = (operand1 * operand2) + "";
                            break;
                        case "/":
                            value = (operand1 / operand2) + "";
                            break;
                        default:
                            throw new IllegalArgumentException("Unrecognized token.");
                    }
                    type = Token.NUMBER;
                    data = value;
                    children[0] = null;
                    children[1] = null;
                    children[2] = null;
                    children = null;
                }
            }
        }
    }

    public void print () {
        if (type == 3) {
            if (showParenthesis) System.out.print("(");
            for (int i = 0; i < children.length; i++) {
                children[i].print();
            }
            if (showParenthesis) System.out.print(")");
        } else {
            if (showParenthesis) System.out.print("(");
            System.out.print(data);
            if (showParenthesis) System.out.print(")");
        }
    }
}
