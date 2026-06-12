//does not work. Not in use
package org.rtsang.cpu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CCompiler {

    public static void main(String[] args) throws IOException
    {
        String fileName = "";

        File myfile = new File("Programs\\C\\" + fileName);
        Scanner filereader = new Scanner(myfile);

        FileWriter fw = new FileWriter("Programs\\Assembly\\" + fileName);
        PrintWriter output = new PrintWriter(fw);


    }
    private String lex(String input) {
        
        return "";
    }

}
