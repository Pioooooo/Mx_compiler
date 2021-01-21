package util.error;

import util.Position;

abstract public class Error extends RuntimeException {
    private final Position pos;
    private final String msg;

    public Error(String msg, Position pos) {
        this.msg = msg;
        this.pos = pos;
    }

    public String toString() {
        return pos.toString() + ": " + msg;
    }
}