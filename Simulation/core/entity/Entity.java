package org.rtsang.core.entity;

import org.joml.Vector3f;


public class Entity {
    private Model model;
    private Vector3f pos, rotation;
    private float scale;

    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }
    public void incPos(Vector3f position)
    {
        pos.x += position.x;
        pos.y += position.y;
        pos.z += position.z;
    }
    public void setPos(Vector3f position)
    {
        pos = position;
    }
    public void incRotation(Vector3f rotation)
    {
        this.rotation.x += rotation.x;
        this.rotation.y += rotation.y;
        this.rotation.z += rotation.z;
    }
    public void setRotation(Vector3f rotation)
    {
        this.rotation = rotation;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }
    public float getScale() {
        return scale;
    }

}
