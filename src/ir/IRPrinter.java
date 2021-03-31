package ir;

import ir.type.FunctionType;
import ir.type.StructType;
import ir.values.GlobalPointer;
import ir.values.GlobalString;

import java.io.PrintStream;
import java.util.stream.Collectors;

public class IRPrinter {
    int valueCnt;
    PrintStream o;

    public void print(Module m, PrintStream o) {
        this.o = o;
        m.builtinFunctions.values().forEach(f -> printFunction(f, true));
        o.println();
        m.structTypes.values().forEach(this::printType);
        o.println();
        m.globals.forEach(this::printGlobal);
        m.constantStrings.values().forEach(this::printConstantString);
        m.functions.values().forEach(f -> printFunction(f, false));
    }

    void printType(StructType t) {
        o.println("%struct." + t.num + " = type " + t.getFullString());
    }

    void printGlobal(GlobalPointer g) {
        o.println("@g." + g.num + " = global " + g.type.getBaseType() + " zeroinitializer, align " + g.type.size() / 8);
    }

    void printConstantString(GlobalString s) {
        o.println("@.str." + s.num + " = private unnamed_addr constant " + s.type.getBaseType() + " c\"" + s.getIRString() + "\", align 1");
    }

    void printFunction(Function function, boolean isBuiltin) {
        if (!isBuiltin) {
            o.println();
        }
        o.println((isBuiltin ? "declare " : "define ")
                + function.getRetType() + " " + function + "("
                + (isBuiltin ? ((FunctionType) function.type).params.stream().map(Type::toString) : function.getArgs().stream().map(a -> a.type + " " + a)).collect(Collectors.joining(", "))
                + ")" + (isBuiltin ? "" : " {"));
        if (isBuiltin) {
            return;
        }
        valueCnt = function.getArgs().size();
        function.basicBlockList.forEach(this::printBasicBlock);
        o.println("}");
    }

    void printBasicBlock(BasicBlock basicBlock) {
        if (basicBlock.instList.iterator().hasNext()) {
            o.println("block." + basicBlock.num + ":");
            basicBlock.instList.forEach(this::printInst);
        }
    }

    void printInst(Inst inst) {
        if (inst.hasRet()) {
            inst.num = valueCnt++;
        }
        o.println("  " + inst.getFullInst());
    }
}
