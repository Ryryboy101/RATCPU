package org.rtsang.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private float pitch, yaw;

    public Camera(){
        //start by facing to positive Z
        position = new Vector3f(0,0,0);
        pitch = 0f;
        yaw = -90.0f;

    }

    public Camera(Vector3f position, float pitch, float yaw) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    public void movePosition(float x, float y, float z){
        if(z != 0){
            position.x += (float) Math.cos(Math.toRadians(yaw)) * z;
            position.z += (float) Math.sin(Math.toRadians(yaw)) * z;
        }
        if(x != 0){
            position.x += (float) -Math.sin(Math.toRadians(yaw)) * x;
            position.z += (float)  Math.cos(Math.toRadians(yaw)) * x;
        }
        position.y += y;
    }
    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    public void setRotation(float pitch, float yaw){
        this.pitch = pitch;
        this.yaw = yaw;
    }
    public void moveRotation(float pitch, float yaw){
        this.pitch += pitch;
        this.yaw += yaw;

        if(this.pitch > 89)
            this.pitch = 89;
        if(this.pitch < -89)
            this.pitch = -89;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f();
        cameraFront.x = (float)(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        cameraFront.y = (float)(Math.sin(Math.toRadians(pitch)));
        cameraFront.z = (float)(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        cameraFront.normalize();
        return new Matrix4f().lookAt(position, new Vector3f(position).add(cameraFront), new Vector3f(0, 1, 0));
    }

    public Vector3f getPositionLookingAt(float distance)
    {
        Vector3f cameraFront = new Vector3f();
        cameraFront.x = (float)(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        cameraFront.y = (float)(Math.sin(Math.toRadians(pitch)));
        cameraFront.z = (float)(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        cameraFront.normalize();
        cameraFront.mul(distance);
        return new Vector3f(position).add(cameraFront);
    }

}
