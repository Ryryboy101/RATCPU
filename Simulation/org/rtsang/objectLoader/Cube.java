package org.rtsang.objectLoader;

import org.joml.Vector3f;

public class Cube extends GameObject {
    private Plane[] faces;
    private String texture;


    public Cube(Vector3f position, Vector3f velocity, Vector3f acceleration,
                Vector3f scale, Vector3f rotation, String texture, float mass) {

        super(position, velocity, acceleration, rotation, scale, mass);
        this.texture = texture;
        faces = new Plane[6];
        buildFaces(scale, texture);
    }

    public Cube(Vector3f position, Vector3f velocity, Vector3f acceleration,
                Vector3f scale, Vector3f rotation, String texture) {
        this(position, velocity, acceleration, scale, rotation, texture, 1.0f);
    }
    public void setTexture(String texture) {
        this.texture = texture;
        for (Plane face : faces) {
            face.setTexture(texture);
        }
    }
    public void setFaceTexture(int faceIndex, String texture) {
        if (faceIndex < 0 || faceIndex >= faces.length) {
            throw new IllegalArgumentException("Face index must be 0-5, got: " + faceIndex);
        }
        faces[faceIndex].setTexture(texture);
    }

    @Override
    public Plane[] getFaces() {
        return faces;
    }

    public String getTexture() {
        return texture;
    }

    private void buildFaces(Vector3f scale, String tex) {
        float halfL = scale.x / 2f;
        float halfH = scale.y / 2f;
        float halfW = scale.z / 2f;

        float l = scale.x, h = scale.y, w = scale.z;

        faces[0] = new Plane(-halfL, -halfH, -halfW, l, h, 0, 0, 0, tex); // Front
        faces[1] = new Plane(halfL, -halfH, halfW, l, h, 0, 180, 0, tex); // Back
        faces[2] = new Plane(-halfL, -halfH, -halfW, w, h, 0, -90, 0, tex); // Left
        faces[3] = new Plane(halfL, -halfH, halfW, w, h, 0, 90, 0, tex); // Right
        faces[4] = new Plane(-halfL, -halfH, halfW, l, w, -90, 0, 0, tex); // Bottom
        faces[5] = new Plane(-halfL, halfH, -halfW, l, w, 90, 0, 0, tex); // Top

        //generate and check normal vectors
        Vector3f localCenter = new Vector3f(0f, 0f, 0f);
        for (Plane face : faces) {
            face.ensureNormalPointsAwayFrom(localCenter);
        }
    }


}