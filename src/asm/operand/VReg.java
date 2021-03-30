package asm.operand;

public class VReg extends Register {
    public static VReg create() {
        return new VReg();
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
