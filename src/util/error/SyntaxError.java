package util.error;
import util.Position;

public class SyntaxError extends Error {

    public SyntaxError(String msg, Position pos) {
        super("Syntax Error:" + msg, pos);
    }

}