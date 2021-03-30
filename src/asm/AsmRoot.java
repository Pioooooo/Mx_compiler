package asm;

import asm.operand.PReg;
import ir.values.GlobalPointer;
import ir.values.GlobalString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class AsmRoot {
    public HashMap<String, AsmFunction> functions = new HashMap<>();
    public ArrayList<GlobalPointer> globals = new ArrayList<>();
    public HashMap<String, GlobalString> constantStrings = new HashMap<>();
    public HashMap<String, PReg> pRegNameMap = new HashMap<>();
    public HashMap<Integer, PReg> pRegIdMap = new HashMap<>();
    static public String[] regName = new String[]{"zero", "ra", "sp", "gp", "tp", "t0", "t1",
            "t2", "s0", "s1", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "s2", "s3", "s4",
            "s5", "s6", "s7", "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6"};

    public AsmRoot() {
        for (int i = 0; i < 32; i++) {
            PReg a = PReg.create(regName[i]);
            pRegNameMap.put(regName[i], a);
            pRegIdMap.put(i, a);
        }
    }

    public PReg getPReg(String name) {
        return pRegNameMap.get(name);
    }

    public PReg getPReg(int id) {
        return pRegIdMap.get(id);
    }

    public ArrayList<PReg> getCallerSave() {
        ArrayList<PReg> ans = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
            ans.add(getPReg(i));
        }
        for (int i = 5; i <= 7; i++) {
            ans.add(getPReg(i));
        }
        for (int i = 10; i <= 17; i++) {
            ans.add(getPReg(i));
        }
        for (int i = 28; i <= 31; i++) {
            ans.add(getPReg(i));
        }
        return ans;
    }

    public ArrayList<PReg> getCalleeSave() {
        ArrayList<PReg> ret = new ArrayList<>();
        for (int i = 8; i <= 9; i++) {
            ret.add(getPReg(i));
        }
        for (int i = 18; i <= 27; i++) {
            ret.add(getPReg(i));
        }
        return ret;
    }

    public ArrayList<PReg> getColors() {
        ArrayList<PReg> ret = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            ret.add(getPReg(i));
        }
        for (int i = 10; i <= 17; i++) {
            ret.add(getPReg(i));
        }
        for (int i = 28; i <= 31; i++) {
            ret.add(getPReg(i));
        }
        for (int i = 8; i <= 9; i++) {
            ret.add(getPReg(i));
        }
        for (int i = 18; i <= 27; i++) {
            ret.add(getPReg(i));
        }
        for (int i = 1; i <= 1; i++) {
            ret.add(getPReg(i));
        }
        return ret;
    }

    public ArrayList<PReg> getPReg() {
        ArrayList<PReg> ret = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            ret.add(getPReg(i));
        }
        return ret;
    }
}
