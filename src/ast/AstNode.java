package ast;

import util.Position;

abstract public class AstNode {
    public Position pos;

    protected AstNode(Position pos) {
        this.pos = pos;
    }

    public abstract <Ty> Ty accept(AstVisitor<Ty> visitor);
}
