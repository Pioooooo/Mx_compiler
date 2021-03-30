package asm.operand;

public class PReg extends Register {
    String name;

    PReg(String name) {
        this.name = name;
    }

    public static PReg create(String name) {
        return new PReg(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
