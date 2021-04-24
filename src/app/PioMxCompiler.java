package app;

import asm.AsmPrinter;
import asm.AsmRoot;
import ast.AstPrinter;
import ast.Nodes;
import codegen.AsmBuilder;
import codegen.RegAllocator;
import frontend.AstBuilder;
import frontend.SemanticChecker;
import frontend.SymbolCollector;
import frontend.TypeCollector;
import frontend.scope.Scope;
import ir.IRBuilder;
import ir.IRPrinter;
import ir.Module;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi.Style;
import picocli.CommandLine.Help.ColorScheme;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import recognizer.MxLexer;
import recognizer.MxParser;
import transforms.Optimizer;
import transforms.SCCP;
import transforms.util.CleanUp;
import transforms.util.MemToReg;
import util.MxErrorListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;

@Command(name = "PioMxCompiler", mixinStandardHelpOptions = true, version = "0.1")
public class PioMxCompiler implements Callable<Integer> {
    @Option(names = "-o", description = "the output file", converter = PrintStreamConverter.class)
    PrintStream out;

    @Parameters(arity = "0")
    File input;

    @ArgGroup()
    Stage stage;

    static class Stage {
        @Option(names = "--dump-ast", description = "dump the abstract syntax tree")
        boolean dumpAst;
        @Option(names = {"-s", "--semantic"}, description = "do semantic check")
        boolean semantic;
        @Option(names = "-emit-llvm", description = "output llvm ir")
        boolean printIR;
        @Option(names = {"-c", "--codegen"}, description = "do code generation")
        boolean codeGen;
        @Option(names = {"-O", "--optimize"}, description = "do code generation")
        boolean optimize;
    }

    @Override
    public Integer call() {
        try {
            if (input == null) {
                System.err.println("ready >>> ");
            }
            if (stage != null && stage.printIR) {
                stage.optimize = true;
            }
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input == null ? System.in : new FileInputStream(input)));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MxParser parser = new MxParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree root = parser.translationUnit();
            Nodes.RootNode astRoot = (Nodes.RootNode) new AstBuilder().visit(root);
            if (stage != null && stage.dumpAst) {
                new AstPrinter(stage.codeGen || out == null ?
                        new PrintStream(new FileOutputStream((input == null ? "a" : input.getAbsolutePath()) + ".ast.dump"))
                        : out).visit(astRoot);
                System.err.println("ast dumped");
                return 0;
            }
            Scope globalScope = new Scope(null);
            Module module = new Module();
            new SymbolCollector(globalScope).visit(astRoot);
            new TypeCollector(globalScope, module).visit(astRoot);
            new SemanticChecker(globalScope, module).visit(astRoot);
            if (stage != null && stage.semantic) {
                return 0;
            }
            IRBuilder irBuilder = new IRBuilder(globalScope, module);
            irBuilder.visit(astRoot);
            new CleanUp(module).run();
            new MemToReg(module).run();
            if (stage != null && stage.optimize) {
                new Optimizer(module).run();
            }
            if (stage != null && stage.printIR) {
                IRPrinter printer = new IRPrinter();
                printer.print(module, stage.codeGen || out == null ?
                        new PrintStream(new FileOutputStream((input == null ? "a" : input.getAbsolutePath()) + ".ll"))
                        : out);
                System.err.println("ir printed");
                return 0;
            }
            AsmRoot asmRoot = new AsmRoot();
            new AsmBuilder(module, asmRoot).run();
            new RegAllocator(asmRoot).run();
            new AsmPrinter().print(asmRoot, out == null ?
                    new PrintStream(new FileOutputStream((input == null ? "a" : input.getAbsolutePath()) + ".s"))
                    : out);
            System.err.println("built");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return 0;
    }

    public static void main(String... args) {
        ColorScheme colorScheme = new ColorScheme.Builder()
                .commands(Style.bold, Style.underline)
                .options(Style.fg_yellow)
                .parameters(Style.fg_yellow)
                .optionParams(Style.italic)
                .errors(Style.fg_red, Style.bold)
                .stackTraces(Style.italic)
                .build();
        System.exit(new CommandLine(new PioMxCompiler()).setColorScheme(colorScheme).execute(args));
    }
}
