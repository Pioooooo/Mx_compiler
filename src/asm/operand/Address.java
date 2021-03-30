package asm.operand;

public class Address extends Immediate {
    public String name;

    Address(int val, String name) {
        super(val);
        this.name = name;
    }

    public static Address create(int val, String name) {
        return new Address(val, name);
    }

    @Override
    public String toString() {
        return "%" + (val > 0 ? "hi" : "lo") + "(" + name + ")";
    }
}
