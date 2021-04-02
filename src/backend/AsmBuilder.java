package backend;

import asm.*;
import asm.inst.*;
import asm.operand.Address;
import asm.operand.Immediate;
import asm.operand.Register;
import asm.operand.VReg;
import ir.Module;
import ir.*;
import ir.inst.*;
import ir.type.StructType;
import ir.values.*;
import util.error.InternalError;

import java.util.HashMap;

public class AsmBuilder {
    Module m;
    AsmRoot root;

    AsmFunction currentFunction;
    AsmBlock currentBlock;
    int offset;

    public AsmBuilder(Module m, AsmRoot root) {
        this.m = m;
        this.root = root;
    }

    public void build() {
        root.globals = m.globals;
        root.constantStrings = m.constantStrings;
        m.functions.values().forEach(this::buildFunction);
        HashMap<String, AsmFunction> functions = new HashMap<>();
        root.functions.values().stream().filter(f -> f.built).forEach(f -> functions.put(f.name, f));
        root.functions = functions;
    }

    public void buildFunction(Function function) {
        currentFunction = get(function);
        currentFunction.built = true;
        currentBlock = get(function.getHead().get());
        root.functions.put(currentFunction.name, currentFunction);
        function.getArgs().forEach(a -> currentFunction.args.add(getReg(a, true)));
        root.getCalleeSave().forEach(r -> {
            VReg tmp = VReg.create();
            currentFunction.calleeSaveVReg.add(tmp);
            Mv.create(tmp, r, currentBlock);
        });
        VReg tmp = VReg.create();
        currentFunction.raSaveVReg = tmp;
        Mv.create(tmp, root.getPReg("ra"), currentBlock);
        for (int i = 0; i < Math.min(currentFunction.args.size(), 8); i++) {
            Mv.create(currentFunction.args.get(i), root.getPReg("a" + i), currentBlock);
        }
        if (currentFunction.args.size() > 8) {
            offset = 0;
            Calc.createI(root.getPReg("s0"), Calc.OpType.addi, root.getPReg("sp"), Immediate.create(-((currentFunction.args.size() - 8) * 4)), currentBlock);
            for (int i = 8; i < currentFunction.args.size(); i++) {
//                Load.createW(currentFunction.args.get(i), root.getPReg("sp"), Immediate.create(offset, true), currentBlock);
                Load.createW(currentFunction.args.get(i), root.getPReg("s0"), Immediate.create(offset), currentBlock);
                offset += 4;
            }
        }
        function.basicBlockList.forEach(this::buildBlock);
        currentFunction = null;
    }

    void buildBlock(BasicBlock block) {
        currentBlock = get(block);
        currentBlock.name = "." + currentFunction.name + "." + block.getName();
        block.instList.forEach(this::buildInst);
        currentBlock = null;
    }

    public void buildInst(Inst inst) {
        if (inst instanceof AllocaInst) { // TODO: mem2reg
            ((AllocaInst) inst).offset = currentFunction.spOffset;
            if (currentFunction.spOffset >= 2048) {
                VReg off = VReg.create();
                AsmInst head = Calc.createI(root.getPReg("sp"), Calc.OpType.add, root.getPReg("sp"), off, currentBlock);
                Li.create(off, Immediate.create(-currentFunction.spOffset), head);
            } else {
                Calc.createI(getReg(inst), Calc.OpType.addi, root.getPReg("sp"), Immediate.create(currentFunction.spOffset), currentBlock);
            }
            currentFunction.spOffset += 4;
            return;
        }
        if (inst instanceof Binary) {
            Calc.OpType op;
            Register rd = getReg(inst), rs1;
            Operand rs2;
            op = switch (((Binary) inst).opType) {
                case mul -> Calc.OpType.mul;
                case sdiv -> Calc.OpType.div;
                case srem -> Calc.OpType.rem;
                case shl -> Calc.OpType.sll;
                case ashr -> Calc.OpType.sra;
                case and -> Calc.OpType.and;
                case or -> Calc.OpType.or;
                case xor -> Calc.OpType.xor;
                case sub -> Calc.OpType.sub;
                case add -> Calc.OpType.add;
            };
            switch (op) {
                case mul, div, rem -> {
                    rs1 = getReg(((Binary) inst).lhs);
                    rs2 = getReg(((Binary) inst).rhs);
                    Calc.createI(rd, op, rs1, rs2, currentBlock);
                    return;
                }
            }
            if (((Binary) inst).rhs instanceof ConstantInt) {
                rs1 = getReg(((Binary) inst).lhs);
                rs2 = Immediate.create(((ConstantInt) ((Binary) inst).rhs).val);
                if (op == Calc.OpType.sub) {
                    op = Calc.OpType.addi;
                    ((Immediate) rs2).val *= -1;
                } else {
                    op = Calc.OpType.valueOf(op + "i");
                }
                Calc.createI(rd, op, rs1, rs2, currentBlock);
                return;
            }
            if (((Binary) inst).lhs instanceof ConstantInt) {
                switch (op) {
                    case sll, sra, sub -> {
                    }
                    default -> {
                        rs1 = getReg(((Binary) inst).rhs);
                        rs2 = Immediate.create(((ConstantInt) ((Binary) inst).lhs).val);
                        op = Calc.OpType.valueOf(op + "i");
                        Calc.createI(rd, op, rs1, rs2, currentBlock);
                        return;
                    }
                }
            }
            rs1 = getReg(((Binary) inst).lhs);
            rs2 = getReg(((Binary) inst).rhs);
            Calc.createI(rd, op, rs1, rs2, currentBlock);
            return;
        }
        if (inst instanceof BitCastInst) {
            Mv.create(getReg(inst), getReg(((BitCastInst) inst).val), currentBlock);
            return;
        }
        if (inst instanceof BrInst) {
            if (((BrInst) inst).cond == null) {
                J.create(get(((BrInst) inst).trueDest), currentBlock);
                return;
            }
            if (inst.getParent().getHead().hasNext() && inst.getParent().getTail().previous() instanceof Icmp && inst.getParent().getTail().previous() == ((BrInst) inst).cond) {
                Icmp cmp = (Icmp) inst.getParent().getTail().previous();
                Branch.OpType op = switch (cmp.op) {
                    case slt -> Branch.OpType.blt;
                    case sgt -> Branch.OpType.bgt;
                    case sle -> Branch.OpType.ble;
                    case sge -> Branch.OpType.bge;
                    case eq -> Branch.OpType.beq;
                    case ne -> Branch.OpType.bne;
                };
                Branch.create(op, getReg(cmp.lhs), getReg(cmp.rhs), get(((BrInst) inst).trueDest), currentBlock);
            } else {
                Branch.create(Branch.OpType.bne, getReg(((BrInst) inst).cond), root.getPReg("zero"), get(((BrInst) inst).trueDest), currentBlock);
            }
            J.create(get(((BrInst) inst).falseDest), currentBlock);
            return;
        }
        if (inst instanceof CallInst) {
            for (int i = 0; i < Integer.min(((CallInst) inst).args.size(), 8); i++) {
                Mv.create(root.getPReg("a" + i), getReg(((CallInst) inst).args.get(i)), currentBlock);
            }
            if (((CallInst) inst).args.size() > 8) {
                int offset = -(((CallInst) inst).args.size() - 8) * 4;
                for (int i = 8; i < ((CallInst) inst).args.size(); i++) {
                    Store.createW(getReg(((CallInst) inst).args.get(i)), root.getPReg("sp"), Immediate.create(offset), currentBlock);
                    offset += 4;
                }
            }
            Call.create(get(((CallInst) inst).function), currentBlock);
            if (inst.hasRet()) {
                Mv.create(getReg(inst), root.getPReg("a0"), currentBlock);
            }
            return;
        }
        if (inst instanceof Icmp) {
            VReg tmp = VReg.create();
            switch (((Icmp) inst).op) {
                case slt -> Calc.createI(getReg(inst), Calc.OpType.slt, getReg(((Icmp) inst).lhs), getReg(((Icmp) inst).rhs), currentBlock);
                case sgt -> Calc.createI(getReg(inst), Calc.OpType.slt, getReg(((Icmp) inst).rhs), getReg(((Icmp) inst).lhs), currentBlock);
                case sle -> {
                    Calc.createI(tmp, Calc.OpType.slt, getReg(((Icmp) inst).rhs), getReg(((Icmp) inst).lhs), currentBlock);
                    Calc.createI(getReg(inst), Calc.OpType.xori, tmp, Immediate.create(1), currentBlock);
                }
                case sge -> {
                    Calc.createI(tmp, Calc.OpType.slt, getReg(((Icmp) inst).lhs), getReg(((Icmp) inst).rhs), currentBlock);
                    Calc.createI(getReg(inst), Calc.OpType.xori, tmp, Immediate.create(1), currentBlock);
                }
                case eq -> {
                    Calc.createI(tmp, Calc.OpType.xor, getReg(((Icmp) inst).lhs), getReg(((Icmp) inst).rhs), currentBlock);
                    Calc.createI(getReg(inst), Calc.OpType.sltiu, tmp, Immediate.create(1), currentBlock);
                }
                case ne -> {
                    Calc.createI(tmp, Calc.OpType.xor, getReg(((Icmp) inst).lhs), getReg(((Icmp) inst).rhs), currentBlock);
                    Calc.createI(getReg(inst), Calc.OpType.sltu, root.getPReg("zero"), tmp, currentBlock);
                }
            }
            return;
        }
        if (inst instanceof GetElementPtrInst) {
            Register ptrVal = getReg(((GetElementPtrInst) inst).ptrVal);
            Type type = ((GetElementPtrInst) inst).ptrVal.getType().getBaseType();
            int offset = 0;
            if (((GetElementPtrInst) inst).indexes.size() == 2) {
                if (type instanceof StructType) {
                    offset = ((StructType) type).getOffset(((ConstantInt) ((GetElementPtrInst) inst).indexes.get(1)).val) / 8;
                } else if (((ConstantInt) ((GetElementPtrInst) inst).indexes.get(1)).val != 0) {
                    throw new InternalError("unsupported use of GEP");
                }
            }
            if (((GetElementPtrInst) inst).indexes.get(0) instanceof ConstantInt) {
                offset += type.size() / 8 * ((ConstantInt) ((GetElementPtrInst) inst).indexes.get(0)).val;
                Calc.createI(getReg(inst), Calc.OpType.addi, ptrVal, Immediate.create(offset), currentBlock);
            } else {
                VReg tmp = VReg.create();
                Calc.createI(tmp, Calc.OpType.mul, getReg(ConstantInt.get(type.m, 32, type.size() / 8)),
                        getReg(((GetElementPtrInst) inst).indexes.get(0)), currentBlock);
                if (offset == 0) {
                    Calc.createI(getReg(inst), Calc.OpType.add, ptrVal, tmp, currentBlock);
                } else {
                    VReg tmp1 = VReg.create();
                    Calc.createI(tmp1, Calc.OpType.add, ptrVal, tmp, currentBlock);
                    Calc.createI(getReg(inst), Calc.OpType.addi, tmp1, Immediate.create(offset), currentBlock);
                }
            }
            return;
        }
        if (inst instanceof LoadInst) {
            if (((LoadInst) inst).ptr instanceof GlobalPointer) {
                VReg tmp = VReg.create();
                Lui.create(tmp, Address.create(1, ((LoadInst) inst).ptr.getName()), currentBlock);
                Load.createW(getReg(inst), tmp, Address.create(0, ((LoadInst) inst).ptr.getName()), currentBlock);
            } else {
                Load.createW(getReg(inst), getReg(((LoadInst) inst).ptr), Immediate.create(0), currentBlock);
            }
            return;
        }
        if (inst instanceof RetInst) {
            if (((RetInst) inst).val != null) {
                assign(root.getPReg("a0"), ((RetInst) inst).val);
            }
            for (int i = 0; i < root.getCalleeSave().size(); i++) {
                Mv.create(root.getCalleeSave().get(i), currentFunction.calleeSaveVReg.get(i), currentBlock);
            }
            Mv.create(root.getPReg("ra"), currentFunction.raSaveVReg, currentBlock);
            Ret.create(currentBlock);
            currentFunction.addTail(currentBlock);
            return;
        }
        if (inst instanceof StoreInst) {
            if (((StoreInst) inst).ptr instanceof GlobalPointer) {
                VReg tmp = VReg.create();
                Lui.create(tmp, Address.create(1, (((StoreInst) inst).ptr).getName()), currentBlock);
                Store.createW(getReg(((StoreInst) inst).val), tmp, Address.create(0, ((StoreInst) inst).ptr.getName()), currentBlock);
            } else {
                Store.createW(getReg(((StoreInst) inst).val, true), getReg(((StoreInst) inst).ptr), Immediate.create(0), currentBlock);
            }
            return;
        }
        throw new InternalError("unrecognized inst");
    }

    public void assign(Register reg, Value val) {
        if (val instanceof Inst || val instanceof GlobalPointer || val instanceof Argument) {
            Mv.create(reg, getReg(val), currentBlock);
        } else if (val instanceof GlobalString) {
            VReg tmp = VReg.create();
            Lui.create(tmp, Address.create(1, val.getName()), currentBlock);
            Calc.createI(reg, Calc.OpType.add, tmp, Address.create(0, val.getName()), currentBlock);
        } else {
            int value = 0;
            if (val instanceof ConstantInt) {
                value = ((ConstantInt) val).val;
            }
            Li.create(reg, Immediate.create(value), currentBlock);
        }
    }

    AsmFunction get(Function function) {
        if (function.asmFunction == null) {
            function.asmFunction = AsmFunction.create(function.getName());
            root.functions.put(function.getName(), function.asmFunction);
            function.asmFunction.setParent(root);
        }
        return function.asmFunction;
    }

    AsmBlock get(BasicBlock block) {
        if (block.asmBlock == null) {
            block.asmBlock = AsmBlock.create(block.loopDepth, get(block.getParent()));
        }
        return block.asmBlock;
    }

    Register getReg(Value val) {
        if (val instanceof GlobalPointer) {
            if (val instanceof ConstantPointerNull) {
                return root.getPReg("zero");
            }
            throw new InternalError("calling getReg on GlobalPointer");
        }
        if (val instanceof Inst) {
            if (val.asmReg == null) {
                val.asmReg = VReg.create();
            }
            return val.asmReg;
        }
        if (val instanceof Argument) {
            if (((Argument) val).ptr != null) {
                return getReg(((Argument) val).ptr);
            }
            if (val.asmReg == null) {
                return val.asmReg = VReg.create();
            }
            return val.asmReg;
        }
        if (val instanceof GlobalString) {
            VReg tmp = VReg.create(), tmp1 = VReg.create();
            Lui.create(tmp, Address.create(1, val.getName()), currentBlock);
            Calc.createI(tmp1, Calc.OpType.addi, tmp, Address.create(0, val.getName()), currentBlock);
            return tmp1;
        }
        int value = 0;
        if (val instanceof ConstantInt) {
            value = ((ConstantInt) val).val;
        }
        VReg tmp = VReg.create();
        Li.create(tmp, Immediate.create(value), currentBlock);
        return tmp;
    }

    Register getReg(Value val, boolean originArgument) {
        if (!originArgument || !(val instanceof Argument)) {
            return getReg(val);
        }
        if (val.asmReg == null) {
            return val.asmReg = VReg.create();
        }
        return val.asmReg;
    }
}
