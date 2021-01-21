package util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Position {
    private final int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(Token token) {
        this.row = token.getLine();
        this.col = token.getCharPositionInLine();
    }

    public Position(TerminalNode terminal) {
        this(terminal.getSymbol());
    }

    public Position(ParserRuleContext ctx) {
        this(ctx.getStart());
    }

    @Override
    public String toString() {
        return row + "," + col;
    }
}
