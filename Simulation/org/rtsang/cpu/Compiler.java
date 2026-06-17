package org.rtsang.cpu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Compiler
{
    private static HashMap<String,Integer> opcode;
    private static HashMap<String, Integer> register;
    private static HashMap<String,Integer> jumpMap;
    private static HashMap<String, Integer> condCodes;
    private static HashMap<String,Integer> bitwise;
    private static HashMap<String, Integer> definitions;

    public static void main(String[] args) throws IOException
    {
        String fileName = "FlappyBird.txt";
        opcode = new HashMap<>();
        opcode.put("noop", 0);
        opcode.put("halt", 1);
        opcode.put("add", 2);
        opcode.put("sub", 3);
        opcode.put("bitwise", 4);
        opcode.put("div", 5);
        opcode.put("mlt", 6);
        opcode.put("io", 7);
        opcode.put("ldi", 8);
        opcode.put("pointer", 9);
        opcode.put("load", 10);
        opcode.put("store", 11);
        opcode.put("call", 12);
        opcode.put("rtn", 13);
        opcode.put("jump", 14);
        opcode.put("branch", 15);

        register = new HashMap<>();
        for(int i=0; i<8; i++) {
            register.put("r" + i, i);
        }

        bitwise = new HashMap<>();
        bitwise.put("or", 0);
        bitwise.put("nor", 1);
        bitwise.put("xor", 2);
        bitwise.put("and", 3);
        bitwise.put("nand", 4);
        bitwise.put("rsh", 5);
        bitwise.put("lsh", 6);

        condCodes = new HashMap<>();
        condCodes.put("brn", 0);
        condCodes.put("brnn", 1);
        condCodes.put("brz", 2);
        condCodes.put("brnz", 3);

        File myfile = new File("Programs/Assembly/" + fileName);
        if (!myfile.exists()) {
            myfile = new File(fileName);
        }

        Scanner filereader = new Scanner(myfile);
        ArrayList<String> code = new ArrayList<>();
        while (filereader.hasNext())
        {
            code.add(filereader.nextLine());
        }
        filereader.close();

        definitions = new HashMap<>();
        jumpMap = new HashMap<>();
        ArrayList<String> cleanCode = new ArrayList<>();
        int addressCounter = 0;

        for (String line : code) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith(";"))
                continue;

            if (line.startsWith(".")) {
                jumpMap.put(line.substring(1), addressCounter);
            } else if(line.toUpperCase().startsWith("DEFINE")) {
                String[] tokens = line.split("\\s+");
                definitions.put(tokens[1], Integer.parseInt(tokens[2]));
            } else {
                cleanCode.add(line);
                addressCounter++;
            }
        }

        FileWriter fw = new FileWriter("Programs/Binary/" + fileName);
        PrintWriter output = new PrintWriter(fw);

        for (String line : cleanCode) {
            String pseudoCheck = checkPseudoInst(line);
            int binValue = convert(pseudoCheck);
            String binString = Integer.toBinaryString(binValue);
            output.println(String.format("%16s", binString).replace(' ', '0'));
        }
        output.close();
        fw.close();
    }

    private static int convert(String code)
    {
        String[] indString = code.trim().split("\\s+");
        int converted = 0;
        String inst = indString[0].toLowerCase();
        //System.out.println(inst);
        switch(inst)
        {
            case "noop":
                break;
            case "hlt":
            case "halt":
                converted = opcode.get(inst);
                break;
            case "add":
            case "sub":
            case "div":
                converted = register.get(indString[1]) << 13 | register.get(indString[2]) << 10 | register.get(indString[3]) << 7  | opcode.get(inst);
                break;
            case "mlt":
                converted = register.get(indString[1]) << 13 | register.get(indString[2]) << 10 | register.get(indString[3]) << 7 | register.get(indString[4])<<4 | opcode.get(inst);
                break;
            case "or":
            case "xor":
            case "nor":
            case "and":
            case "nand":
            case "rsh":
            case "lsh":
                converted = (register.get(indString[1]) << 13) |
                        (register.get(indString[2]) << 10) |
                        (register.get(indString[3]) << 7)  |
                        (bitwise.get(inst) << 4)           |
                        opcode.get("bitwise");
                break;
            case "ldi":
                if(definitions.containsKey(indString[2]))
                {
                    converted = register.get(indString[1]) << 13 | (definitions.get(indString[2]) & 0x1FF) << 4 | opcode.get(inst);
                }
                else
                    converted = register.get(indString[1]) << 13 | (Integer.parseInt(indString[2]) & 0x1FF) << 4 | opcode.get(inst);
                break;
            case "load":
            case "store":
            case "pointer":
            case "io":
                int ioAddr = Integer.parseInt(indString[2]) & 0xFF;
                int dir = (indString.length > 3 && indString[3].equalsIgnoreCase("read")) ? 1 : 0;
                converted = (register.get(indString[1]) << 13)
                        | (ioAddr << 5)
                        | (dir    << 4)
                        | opcode.get(inst);
                break;
            case "jump":
            case "call":
                int targetAddress = jumpMap.containsKey(indString[1]) ? jumpMap.get(indString[1]) : Integer.parseInt(indString[1]);
                converted = ((targetAddress & 0x1FF) << 4) | opcode.get(inst);
                break;
            case "rtn":
                converted = converted | opcode.get("rtn");
                break;
            case "brn":
            case "brnn":
            case "brz":
            case "brnz":
                int branchTarget = jumpMap.containsKey(indString[1]) ? jumpMap.get(indString[1]) : Integer.parseInt(indString[1]);
                converted = (condCodes.get(inst) << 14) | ((branchTarget & 0x1FF) << 4) | opcode.get("branch");
                break;

            default:
                converted = converted | opcode.get("halt");
                break;
        }
        return converted;
    }

    private static String checkPseudoInst(String code) {
        String[] split = code.trim().split("\\s+");
        if (split.length == 0) return code;

        String inst = split[0].toLowerCase();
        switch (inst) {
            case "cmp":
                return "sub " + split[1] + " " + split[2] + " r0";
            case "mov":
                return "add " + split[1] + " r0 " + split[2];
            case "clr":
                return "xor " + split[1] + " " + split[1] + " " + split[1];
            case "not":
                return "nor " + split[1] + " r0 " + split[2];
            default:
                return code;
        }
    }
}