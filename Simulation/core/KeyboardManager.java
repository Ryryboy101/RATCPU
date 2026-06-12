package org.rtsang.core;

import org.lwjgl.glfw.GLFW;

public class KeyboardManager {
//not in use
    public boolean isKeyPressed;
    public int keyPressed;
    public WindowManager window;
    private Camera camera;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public KeyboardManager(){
        isKeyPressed = false;
        keyPressed = 0;

    }
    public void printPos()
    {

    }
    public void setWindow(WindowManager window){
        this.window  = window;
    }

    public WindowManager getWindow() {
        return window;
    }
}
