package util.error;

import util.Position;

public class InternalError extends Error {

    public InternalError(String msg, Position pos) {
        super("Internal Error:" + msg, pos);
    }

    public InternalError(String msg) {
        this("Internal Error:" + msg, new Position(0, 0));
    }

}