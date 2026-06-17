package org.rtsang.cpu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class CPU
{
    private RAM registers;
    private RAM ram;
    public RAM ioPorts;
    private CallStack callStack;
    private ProgramCounter pc;
    private ALU alu;
    private ArrayList<String> code;
    private boolean stop;


    public CPU(String file) throws IOException
    {
        File myFile = new File(file);
        Scanner reader = new Scanner(myFile);

        code = new ArrayList<String>();
        while(reader.hasNextLine())
        {
            code.add(reader.nextLine());
        }
        init();

    }
    private void init()
    {
        alu = new ALU();
        pc = new ProgramCounter(0);
        registers = new RAM(8);
        ram = new RAM(255);
        ioPorts = new RAM(255);
        callStack = new CallStack();
        stop = false;
    }
    public void clock()
    {
        String line = code.get(pc.getInstruction());
        //System.out.println(pc.getInstruction());
        String instruction = line.substring(12);//decoder

        int r1 = Integer.parseInt(line.substring(0,3),2);
        int address = Integer.parseInt(line.substring(4,12),2);


        int r2 = Integer.parseInt(line.substring(3,6),2);
        int r3 = Integer.parseInt(line.substring(6,9),2);
        int r4 = Integer.parseInt(line.substring(9,12),2);

        boolean jumped = false;
        boolean is1 = line.charAt(11)=='1';

        switch (instruction){
            case "0000"://noop
                break;
            case "0001"://hlt
                stop = true;
                 return;
            case "0010"://add
                registers.write(alu.add(registers.read(r1),registers.read(r2)),r3);
                break;
            case "0011"://sub
                registers.write(alu.sub(registers.read(r1),registers.read(r2)),r3);
                break;
            case "0100"://bitwise
                if(r4 == 0)
                    registers.write(alu.or(registers.read(r1),registers.read(r2)),r3);
                else if(r4 == 1)
                    registers.write(alu.nor(registers.read(r1),registers.read(r2)),r3);
                else if(r4 == 2)
                    registers.write(alu.xor(registers.read(r1),registers.read(r2)),r3);
                else if(r4 == 3)
                    registers.write(alu.and(registers.read(r1),registers.read(r2)),r3);
                else if(r4 == 4)
                    registers.write(alu.nand(registers.read(r1),registers.read(r2)),r3);
                else if(r4 == 5)
                    registers.write(alu.rsh(registers.read(r1),registers.read(r2)),r3);
                else if(r4 == 6)
                    registers.write(alu.lsh(registers.read(r1),registers.read(r2)),r3);
                break;
            case "0101"://div
                registers.write(alu.div(registers.read(r1),registers.read(r2)),r3);
                break;
            case "0110":
                byte[] result = alu.mlt(registers.read(r1),registers.read(r2));
                registers.write(result[0],r3);
                registers.write(result[1],r4);
                break;
            case "0111":
                int portAddress = address>>1;
                if (is1)
                    registers.write(ioPorts.read(portAddress), r1);
                else
                    ioPorts.write(registers.read(r1), portAddress);
                break;

            case "1000":
            case "1001":
                registers.write((byte)(address&0xff),r1);
                break;
            case "1010":
                if(is1)
                    registers.write(ram.read(r2),r1);
                else
                    registers.write(ram.read(address>>1),r1);
                break;
            case "1011":
                if(is1)
                    ram.write(registers.read(r1),registers.read(r2));
                else
                    ram.write(registers.read(r1),address>>1);
                break;
            case "1100":
                callStack.push(pc.getInstruction()+1);
                pc.setInstruction(Integer.parseInt(line.substring(3,12), 2));
                jumped = true;
                break;
            case "1101":
                pc.setInstruction(callStack.pop());
                jumped = true;
                break;
            case "1110":
                jumped = true;
                pc.setInstruction( Integer.parseInt(line.substring(3,12),2));
                break;
            case "1111":
                String cond = line.substring(0, 2);
                boolean shouldBranch = false;

                switch (cond) {
                    case "00":
                        shouldBranch = alu.negative;
                        break;
                    case "01":
                        shouldBranch = !alu.negative;
                        break;
                    case "10":
                        shouldBranch = alu.zero;
                        break;
                    case "11":
                        shouldBranch = !alu.zero;
                        break;
                }

                if (shouldBranch) {
                    pc.setInstruction(Integer.parseInt(line.substring(3, 12), 2));
                    jumped = true;
                }
                break;

        }
        if(!jumped)
            pc.increment();

    }

    public boolean isStop() {
        return stop;
    }
}
