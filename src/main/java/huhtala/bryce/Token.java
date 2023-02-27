package huhtala.bryce;

import java.text.DecimalFormat;

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

    public int getDepth() {
        if (type != Token.EXPRESSION) {
            if (showParenthesis) return 999999;
            else return 1;
        }
        return children[0].getDepth() + children[2].getDepth();
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
                return 10000 + (parentheticalLevel+1) * 100000;
            case "*":
            case "/":
                return 20000 + (parentheticalLevel+1) * 100000;
            default:
                return -1;
        }
    }

    public void step() {
        if (type == Token.EXPRESSION) {
            if (children[0].type == Token.EXPRESSION && children[2].type == Token.EXPRESSION) {
                int leftD = children[0].getDepth();
                int rightD = children[2].getDepth();
                if (children[0].getDepth() >= children[2].getDepth())
                    stepLeft();
                else
                    stepRight();
            } else if (children[0].type == Token.EXPRESSION)
                stepLeft();
            else if (children[2].type == Token.EXPRESSION)
                stepRight();
            else {
                if (children[0].type == Token.NUMBER && children[0].showParenthesis)
                    children[0].showParenthesis = false;
                else if (children[2].type == Token.NUMBER && children[2].showParenthesis)
                    children[2].showParenthesis = false;
                else {
                    String value;
                    double operand1 = Double.valueOf(children[0].data);
                    double operand2 = Double.valueOf(children[2].data);
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

    private void stepLeft() {
        if (children[2].type == Token.NUMBER && children[2].showParenthesis)
            children[2].showParenthesis = false;
        else
            children[0].step();
    }

    private void stepRight() {
        if (children[0].type == Token.NUMBER && children[0].showParenthesis)
            children[0].showParenthesis = false;
        else
            children[2].step();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (type == Token.EXPRESSION) {
            if (showParenthesis) stringBuilder.append("(");
            for (int i = 0; i < children.length; i++) {
                stringBuilder.append(children[i].toString());
            }
            if (showParenthesis) stringBuilder.append(")");
        } else {
            if (showParenthesis) stringBuilder.append("(");
            stringBuilder.append(formatData());
            if (showParenthesis) stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    private String formatData() {
        if (type == Token.NUMBER) {
            if (data.contains(".")) {
                double asDouble = Double.valueOf(data);
                Double decimalPortion = asDouble - (double)(int)asDouble;
                if (decimalPortion > 0.0001) {
                    DecimalFormat formater = new DecimalFormat("0.00");
                    return formater.format(Double.valueOf(data));
                } else {
                    DecimalFormat formater = new DecimalFormat("0");
                    return formater.format(Double.valueOf(data));
                }
            } else return data;
        } else {
            return " " + data + " ";
        }
    }

}
