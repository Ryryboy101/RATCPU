package org.rtsang.test;

import org.joml.Vector3f;
import org.rtsang.core.EngineManager;
import org.rtsang.core.WindowManager;
import org.rtsang.core.utils.Consts;
import org.rtsang.cpu.CPU;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

    
public class Launcher {
    private static WindowManager window;
    private static TestGame game;

    public static void main(String[]args) throws Exception {
        window = new WindowManager(Consts.title, 0, 0, false);
        game = new TestGame();
        game.setWindow(window);
        EngineManager engine = new EngineManager();
        engine.setWindow(window);
        engine.setGameLogic(game);

        File video = new File("Programs\\Videos\\badapple.txt");
        Scanner reader = new Scanner(video);
        ArrayList<String> lines = new ArrayList<String>();

        while (reader.hasNextLine()){
            lines.add(reader.nextLine());
        }
        game.setVideo(lines);

        CPU cpu = new CPU("Programs\\Binary\\FlappyBird.txt");
        game.queueCPU(cpu);

        try{
            engine.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}
