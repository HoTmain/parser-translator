package calculator;

public class ParseError extends Exception {
    public ParseError(ParseErrorInput e) {
        super("In "+e.function()+": Wrong input: "+ e.lookahead());
    }
}
