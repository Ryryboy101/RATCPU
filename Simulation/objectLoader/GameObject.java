package org.rtsang.objectLoader;

import org.joml.Vector3f;
import org.rtsang.physics.TranslationalKinematics;
import org.rtsang.physics.RotationalKinematics;
public abstract class GameObject {

    private Vector3f rotation;
    private Vector3f center;
    private Vector3f scale;


    private float mass;

    private Vector3f angularVelocity = new Vector3f(0f, 0f, 0f);

    private float angularDamping = 0.98f;

    private TranslationalKinematics translationalKinematics;
    private RotationalKinematics rotationalKinematics;



    public GameObject(Vector3f position,
                      Vector3f startingVelocity,
                      Vector3f startingAcceleration,
                      Vector3f rotation,
                      Vector3f scale,
                      float mass) {

        this.scale = scale;
        this.rotation = rotation;
        this.mass = mass;

        this.center = new Vector3f(
                position.x + scale.x / 2f,
                position.y + scale.y / 2f,
                position.z + scale.z / 2f);

        translationalKinematics = new TranslationalKinematics(center, startingVelocity, startingAcceleration);
        rotationalKinematics = new RotationalKinematics();
    }



    public void update(float timeInterval) {

        translationalKinematics.update(timeInterval);
        this.center = translationalKinematics.getPosition();

        rotation.x += (float) Math.toDegrees(angularVelocity.x) * timeInterval;
        rotation.y += (float) Math.toDegrees(angularVelocity.y) * timeInterval;
        rotation.z += (float) Math.toDegrees(angularVelocity.z) * timeInterval;

        angularVelocity.mul(angularDamping);
    }

    public void rotate(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    //I think i paid attention in physics this year
    public Vector3f getAngularVelocity() {
        return angularVelocity;
    }

    public void addAngularVelocity(Vector3f delta) {
        angularVelocity.add(delta);
    }

    public void setAngularVelocity(Vector3f angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setAngularDamping(float damping) {
        this.angularDamping = damping;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public TranslationalKinematics getTranslationalKinematics() {
        return translationalKinematics;
    }

    public Vector3f getPosition() {
        return center;
    }

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public float getRotX() {
        return rotation.x;
    }

    public float getRotY() {
        return rotation.y;
    }

    public float getRotZ() {
        return rotation.z;
    }
    public RotationalKinematics getRotationalKinematics() {
        return rotationalKinematics;
    }

    public void setRotationalKinematics(RotationalKinematics rotationalKinematics) {
        this.rotationalKinematics = rotationalKinematics;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setCenter(Vector3f center) {
        this.center = center;
        translationalKinematics.setPosition(center);
    }

    public void setTranslationalKinematics(TranslationalKinematics tk) {
        this.translationalKinematics = tk;
    }

    public abstract Plane[] getFaces();
}