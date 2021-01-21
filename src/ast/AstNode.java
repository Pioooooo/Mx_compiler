package ast;

import util.Position;

abstract public class AstNode {
    public Position pos;

    protected AstNode(Position pos) {
        this.pos = pos;
    }

    public abstract void accept(AstVisitor visitor);
}
