package com.company;

import java.util.Vector;

public class Registers {
    Vector<Register> registers;

    public Registers() {
        this.registers = new Vector<Register>();
    }

    public void addRegisters(Register register) {
        this.registers.add(register);
    }

    @Override
    public String toString() {
        String result = "";
        for (Register register : this.registers) {
            result += register.getStudent().toString() + " ";
            result += register.getCourse().toString() + " ";
            result += register.getGrade() + "\n";
        }
        return "Registers: {" +
                result +
                '}';
    }
}
