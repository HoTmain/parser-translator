package calculator;

public class ParseError extends Exception {
    public ParseError() {
        super("Wrong input");
    }
}
