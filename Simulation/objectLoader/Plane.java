package org.rtsang.objectLoader;

import org.joml.Vector3f;

public class Plane extends GameObject {
    private Vector3f[] vertices;
    private int[] indices;
    private float[] textureCords;
    private String texture;
    private Vector3f normal;


    public Plane(float xpos, float ypos, float zpos,
                 float width, float height,
                 float rotX, float rotY, float rotZ,
                 String texture) {

        super(new Vector3f(xpos, ypos, zpos), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),
                new Vector3f(rotX, rotY, rotZ), new Vector3f(width, height, 1), 1f);

        this.texture = texture;

        indices = new int[]{2, 1, 0, 0, 3, 2};
        textureCords = new float[]{0, 1, 1, 1, 1, 0, 0, 0};

        float[] temp = {
                0, 0, 0,
                width, 0, 0,
                width, height, 0,
                0, height, 0
        };

        vertices = new Vector3f[4];
        for (int i = 0; i < 4; i++) vertices[i] = new Vector3f();

        buildVertices(temp, xpos, ypos, zpos, rotX, rotY, rotZ);
        calculateNormal();
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getTexture() {
        return texture;
    }

    public float[] getTextureCords() {
        return textureCords;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void ensureNormalPointsAwayFrom(Vector3f interiorPoint) {
        Vector3f toFace = vertices[0].sub(interiorPoint, new Vector3f());
        if (normal.dot(toFace) < 0f) normal.negate();
    }

    private void buildVertices(float[] temp, float xpos, float ypos, float zpos,
                               float rotX, float rotY, float rotZ) {
        double radX = Math.toRadians(rotX);
        double radY = Math.toRadians(rotY);
        double radZ = Math.toRadians(rotZ);

        for (int i = 0; i < 4; i++) {
            float x = temp[i * 3];
            float y = temp[i * 3 + 1];
            float z = temp[i * 3 + 2];

            float x1 = x;
            float y1 = (float) (y * Math.cos(radX) - z * Math.sin(radX));
            float z1 = (float) (y * Math.sin(radX) + z * Math.cos(radX));

            float x2 = (float) (x1 * Math.cos(radY) + z1 * Math.sin(radY));
            float y2 = y1;
            float z2 = (float) (-x1 * Math.sin(radY) + z1 * Math.cos(radY));

            float x3 = (float) (x2 * Math.cos(radZ) - y2 * Math.sin(radZ));
            float y3 = (float) (x2 * Math.sin(radZ) + y2 * Math.cos(radZ));
            float z3 = z2;

            this.vertices[i].set(x3 + xpos, y3 + ypos, z3 + zpos);
        }
    }

    private void calculateNormal() {
        Vector3f p0 = vertices[0];
        Vector3f p1 = vertices[1];
        Vector3f p2 = vertices[2];

        Vector3f edge1 = p1.sub(p0, new Vector3f());
        Vector3f edge2 = p2.sub(p0, new Vector3f());
        normal = edge1.cross(edge2, new Vector3f()).normalize();
    }

    @Override
    public Plane[] getFaces() {
        return new Plane[]{this};
    }
}