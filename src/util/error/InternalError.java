package util.error;
import util.Position;

public class InternalError extends Error {

    public InternalError(String msg, Position pos) {
        super("Internal Error:" + msg, pos);
    }

}