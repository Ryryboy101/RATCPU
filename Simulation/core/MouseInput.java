package org.rtsang.core;


import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;


public class MouseInput {
    private WindowManager window;

    public Vector2d getPreviousPos() {
        return previousPos;
    }

    public Vector2d getCurrentPos() {
        return currentPos;
    }

    private final Vector2d previousPos,currentPos;

    private final Vector2f displayVec;

    private boolean inWindow = false, leftButtonPress = false, rightButtonPress = false;

    public MouseInput() {
        previousPos = new Vector2d(-1,-1);
        currentPos = new Vector2d(0,0);
        displayVec = new Vector2f();
    }

    public void init() {

        long windowHandle = window.getWindow();


        if (GLFW.glfwRawMouseMotionSupported())
            GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);

        GLFW.glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        GLFW.glfwSetCursorEnterCallback(windowHandle, (window, entered) -> {
            inWindow = entered;
        });

        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1)
                leftButtonPress = action == GLFW.GLFW_PRESS;
            if (button == GLFW.GLFW_MOUSE_BUTTON_2)
                rightButtonPress = action == GLFW.GLFW_PRESS;
        });


}

    public void input() {
        displayVec.x = 0;
        displayVec.y = 0;


        if (previousPos.x == -1 && previousPos.y == -1) {
            previousPos.x = currentPos.x;
            previousPos.y = currentPos.y;
            return;
        }

        double x = currentPos.x - previousPos.x;
        double y = currentPos.y - previousPos.y;

        if (x != 0) displayVec.y = (float) x;
        if (y != 0) displayVec.x = (float) y;

        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public Vector2f getDisplayVec() {
        return displayVec;
    }

    public boolean isRightButtonPress() {
        return rightButtonPress;
    }

    public boolean isLeftButtonPress() {
        return leftButtonPress;
    }

    public void setWindow(WindowManager window) {
        this.window = window;
    }
}
