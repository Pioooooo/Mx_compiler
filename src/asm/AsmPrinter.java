package asm;

import ir.values.GlobalPointer;
import ir.values.GlobalString;

import java.io.PrintStream;

public class AsmPrinter {
    PrintStream o;

    public void print(AsmRoot root, PrintStream o) {
        this.o = o;
        o.println("\t.text");
        root.functions.values().forEach(this::printFunction);
        o.println("\t.section\t.bss");
        root.globals.forEach(this::printGlobal);
        o.println("\t.section\t.rodata");
        root.constantStrings.values().forEach(this::printConstantString);
    }

    void printFunction(AsmFunction function) {
        if (!function.built) {
            return;
        }
        o.println();
        o.println("\t.globl\t" + function);
        o.println("\t.type\t" + function + ", @function");
        o.println(function.name + ":");
        function.blocks.forEach(this::printBlock);
        o.println("\t.size\t" + function + ", .-" + function);
    }

    public void printBlock(AsmBlock block) {
        o.println(block.name + ":");
        block.instList.forEach(i -> o.println("\t" + i));
    }

    void printGlobal(GlobalPointer g) {
        o.println("\t.globl\t" + g.getName());
        o.println("\t.type\t" + g.getName() + ", @object");
        o.println(g.getName() + ":");
        o.println("\t.zero\t4");
        o.println("\t.size\t" + g.getName() + ", 4");
    }

    void printConstantString(GlobalString s) {
        o.println(s.getName() + ":");
        o.println("\t.string\t\"" + s.val + "\"");
    }
}
