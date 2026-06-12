package org.rtsang.core.utils;

import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }
    public static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }
    public static String loadResource(String fileName) throws Exception{
        String result;
        try(InputStream in = Utils.class.getResourceAsStream(fileName); Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())){
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
    public static List<String> readAllLines(String fileName) throws IOException {
        List<String> list = new ArrayList<String>();
        File myfile = new File(fileName);
        Scanner filereader = new Scanner(myfile);
        while(filereader.hasNextLine())
            list.add(filereader.nextLine());
        return list;
    }
}
