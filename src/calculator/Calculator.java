package calculator;

import java.io.IOException;
import java.io.InputStream;

public class Calculator {
    private final InputStream in;
    private int lookahead; // current parsed token

    public Calculator() throws IOException {
        this.in = System.in;
        lookahead = in.read();
    }

    private int exp() throws IOException, ParseError {
        if (isDigit(lookahead)) {
            int num = evalDigit(lookahead);
            consume(lookahead);
            Exp2Res exp2Res = exp2();
            if (exp2Res == null)
                return num;

            int result = 0;
//            System.out.printf("lookahead is: %c, %n", lookahead);
//            System.out.printf("exp2res: oper %c, num %d\n", exp2Res.oper, exp2Res.num);
            switch (exp2Res.oper) {
                case ('+'):
                    result = num + exp2Res.num;
                case ('-'):
                    result = num - exp2Res.num;
                case ('*'):
                    result = (int) Math.pow(num, exp2Res.num);
            }
            return result;
        }

        throw new ParseError();
    }

    private Exp2Res exp2() throws ParseError, IOException {
        if (acceptableOp()) {
//            System.out.println("acceptable; lookahead is:");
//            System.out.printf("%c\n",lookahead);
            int oper = lookahead;
            int res = exp();
            consume(lookahead);
            return new Exp2Res(lookahead, res);
        }

        if (lookahead == '\n' || lookahead == -1)
            return null;

        throw new ParseError();
    }


    // Helper functions
    private void consume(int c) throws IOException, ParseError {
        if (lookahead != c)
            throw new ParseError();

        lookahead = in.read();
    }

    private boolean isDigit(int c) {
        return '0' <= c && c <= '9';
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

    public int eval() throws ParseError, IOException {
        return exp();
    }
}
