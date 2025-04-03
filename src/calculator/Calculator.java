package calculator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Calculator {
    private final InputStream in;
    private int lookahead; // current parsed token

    public Calculator() throws IOException {
        this.in = System.in;
        lookahead = in.read();
    }

    private int exp() throws IOException, ParseError, OperError {
        if (!isDigit(lookahead) && lookahead != '(')
            throw new ParseError(new ParseErrorInput("exp", (char) lookahead));

        return exp2(term());
    }

    private int exp2(int left) throws ParseError, IOException, OperError {
        if (lookahead == '+' || lookahead == '-') {
            int oper = lookahead;
            consume(lookahead);
            int right = term();
            return exp2(doCalc(left, oper, right));
        }

        if (isEOF(lookahead) || lookahead == ')')
            return left;

        throw new OperError((char) lookahead);
    }

    private int term() throws ParseError, IOException, OperError {
        if (!isDigit(lookahead) && lookahead != '(')
            throw new ParseError(new ParseErrorInput("term", (char) lookahead));

        return term2(factor());
    }

    private int term2(int left) throws ParseError, IOException, OperError {
        if (isExp()) {
            //  ** term()
            return doCalc(left, '*', term());
        }

        if (lookahead == '+' || lookahead == '-' || lookahead == ')' || isEOF(lookahead))
            return left;

        throw new OperError((char) lookahead);
    }

    private int factor() throws ParseError, IOException, OperError {
        if (isDigit(lookahead))
            return num();
        if (lookahead == '(') {
            consume('(');
            int _exp = exp();
            consume(')');
            return _exp;
        }

        throw new ParseError(new ParseErrorInput("factor", (char) lookahead));
    }

    private int num() throws ParseError, IOException {
        ArrayList<Integer> digits = new ArrayList<>();
        do {
            digits.add(lookahead);
            consume(lookahead);
        } while (isDigit(lookahead));

        int exp = 1;
        int number = 0;
        for (int i = digits.size() - 1; i >= 0; i--) {
            number += evalDigit(digits.get(i)) * exp;
            exp *= 10;
        }

        return number;
    }


    // Helper functions
    private void consume(int c) throws IOException, ParseError {
//        System.out.println("Consuming " + (char) lookahead);
        if (lookahead != c)
            throw new ParseError(new ParseErrorInput("consume", (char) lookahead));

        lookahead = in.read();
    }

    private boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    private boolean isEOF(int c) {
        return c == '\n' || c == -1;
    }

    private int evalDigit(int c) {
        return c - '0';
    }

    private boolean isExp() throws ParseError, IOException {
        if (lookahead == '*') {
            consume('*');
            consume('*');
            //  if no issues arose from consuming two '*', then we have exponentiation
            return true;
        }

        return false;
    }

    private int doCalc(int left, int op, int right) throws ParseError {
        return switch (op) {
            case ('+') -> left + right;
            case ('-') -> left - right;
            case ('*') -> (int) Math.pow(left, right);
            default -> throw new ParseError(new ParseErrorInput("doCalc", (char) lookahead));
        };
    }

    public int eval() throws ParseError, IOException, OperError {
        return exp();
    }
}
