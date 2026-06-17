package org.rtsang.cpu;

public class ProgramCounter {
    private int instruction;
    public ProgramCounter(int start)
    {
        instruction = start;
    }
    public void increment()
    {
        instruction++;
    }
    public void setInstruction(int instruction)
    {
        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }
}
