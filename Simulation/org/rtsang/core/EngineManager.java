package org.rtsang.core;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.rtsang.core.utils.Consts;


public class EngineManager {
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 10000;
    public static final float CPU_HZ  = 1000;
    public static final float CPU_FREQUENCY = 1.0f/CPU_HZ;
    public static final float TICK_RATE = 50f;
    public static final float TICK_TIME = 1.0f/TICK_RATE;

    private static int fps;



    private boolean isRunning;

    private WindowManager window;
    private MouseInput mouseInput;
    private GLFWErrorCallback errorCallback;


    private ILogic gameLogic;


    private void init() throws Exception{
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Use the window already set via setWindow()


        mouseInput = new MouseInput();
        mouseInput.setWindow(window);
        window.init();
        mouseInput.init();
        gameLogic.init();

    }

    public void start() throws Exception{
        init();
        if(isRunning)
            return;
        run();
    }



    public void run() {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while (isRunning) {
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();


            while (unprocessedTime > TICK_TIME) {
                update(TICK_TIME);
                unprocessedTime -= TICK_TIME;

                if (window.windowShouldClose())
                    stop();

                if (frameCounter >= NANOSECOND) {
                    setFps(frames);
                    window.setTitle(Consts.title + getFps());
                    frames = 0;
                    frameCounter = 0;
                }

            }


            render();
            frames++;
        }
        cleanup();
    }
    public void stop(){
        if(!isRunning)
            return;
        isRunning = false;
    }

    private void input(){
        mouseInput.input();
        gameLogic.input(mouseInput);
    }
    private void render(){
        gameLogic.render();
        window.update();
    }
    private void update(float interval){
        gameLogic.update(interval);
    }
    private void cleanup(){
        gameLogic.cleanup();
        window.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }

    public void setWindow(WindowManager window) {
        this.window = window;
    }

    public void setGameLogic(ILogic gameLogic) {
        this.gameLogic = gameLogic;
    }

}

