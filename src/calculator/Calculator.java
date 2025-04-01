package calculator;

import java.io.IOException;
import java.io.InputStream;

public class Calculator {
    private final InputStream in;
    private int lookahead; // current parsed token
    private Exp2Res exp2Res;

    public Calculator() throws IOException {
        this.in = System.in;
        lookahead = in.read();
        exp2Res = new Exp2Res('+', 0);
    }

    private int exp() throws IOException, ParseError, OperError {
        if (isDigit(lookahead)) {
            int right = evalDigit(lookahead);
            int new_left = doCalc(exp2Res.num, exp2Res.oper, right);
            consume(lookahead);
            return exp2(new_left);

        }
        else if (isEOF(lookahead))
            return exp2Res.num;

        throw new ParseError(new ParseErrorInput("exp", (char)lookahead));
    }

    private int exp2(int left) throws ParseError, IOException, OperError {
        if (acceptableOp()) {
            this.exp2Res.oper = lookahead;
            consume(lookahead);
            this.exp2Res.num = left;
            return exp();
        }

        if (isEOF(lookahead))
            return left;

        throw new OperError((char)lookahead);
    }


    // Helper functions
    private void consume(int c) throws IOException, ParseError {
        if (lookahead != c)
            throw new ParseError(new ParseErrorInput("consume", (char)lookahead));

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

    private boolean acceptableOp() throws ParseError, IOException {
        if (lookahead == '+' || lookahead == '-')
            return true;
        if (lookahead == '*') {
            consume('*');
            return lookahead == '*';
        }

        return false;
    }

    private int doCalc(int left, int op, int right) throws ParseError {
        return switch (op) {
            case ('+') -> left + right;
            case ('-') -> left - right;
            case ('*') -> (int) Math.pow(left, right);
            default -> throw new ParseError(new ParseErrorInput("doCalc", (char)lookahead));
        };
    }

    public int eval() throws ParseError, IOException, OperError {
        return exp();
    }
}
