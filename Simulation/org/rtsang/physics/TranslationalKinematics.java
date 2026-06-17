package org.rtsang.physics;

import org.joml.Vector3f;

public class TranslationalKinematics {
    private Vector3f velocity;
    private Vector3f acceleration;

    public Vector3f getPosition() {
        return position;
    }

    private Vector3f position;

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public TranslationalKinematics(Vector3f position, Vector3f velocity, Vector3f acceleration) {
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.position = position;
    }

    public void update(float timeInterval)
    {
        updatePosition(timeInterval);
        updateVelocity(timeInterval);
    }
    public void updatePosition(float timeInterval){
        position.x += velocity.x * timeInterval + 0.5f * acceleration.x * timeInterval * timeInterval;
        position.y += velocity.y * timeInterval + 0.5f * acceleration.y * timeInterval * timeInterval;
        position.z += velocity.z * timeInterval + 0.5f * acceleration.z * timeInterval * timeInterval;
    }
    public void updateVelocity(float timeInterval)
    {
         velocity.x += acceleration.x * timeInterval;
         velocity.y += acceleration.y * timeInterval;
         velocity.z += acceleration.z * timeInterval;

    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }
}
