package org.rtsang.cpu;

import java.util.Arrays;

public class RAM {
    private byte[] ram;
    public RAM(int length)
    {
        ram = new byte[length];
    }
    public void write(byte number,int address)
    {
        if(address < ram.length && address != 0)  // zero register used to throw away data. Idea taken from ARM and RISC-V architecture.
            ram[address] = number;
    }
    public byte read(int address)
    {
        if(address < ram.length)
            return ram[address];
        return 0;
    }
}
