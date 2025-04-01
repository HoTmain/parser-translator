package calculator;

public class OperError extends Exception {
    public OperError(char op) {
        super(op+": Unsupported operator");
    }
}
