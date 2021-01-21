import ast.Nodes.*;
import frontend.AstBuilder;
import frontend.SemanticChecker;
import frontend.SymbolCollector;
import frontend.TypeCollector;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


import recognizer.MxLexer;
import recognizer.MxParser;
import util.MxErrorListener;
import util.scope.Scope;


public class Main {
    public static void main(String[] args) throws Exception {
        try {
            MxLexer lexer = new MxLexer(CharStreams.fromStream(System.in));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MxParser parser = new MxParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree root = parser.translationUnit();
            RootNode astRoot = (RootNode) new AstBuilder().visit(root);
            Scope globalScope = new Scope(null);
            new SymbolCollector(globalScope).visit(astRoot);
            new TypeCollector(globalScope).visit(astRoot);
            new SemanticChecker(globalScope).visit(astRoot);
        } catch (Error e) {
            System.err.println(e.toString());
            throw new RuntimeException();
        }
    }
}
