package org.rtsang.objectLoader;

public class Face {
    private String fileName;
    private String texture;

    public Face(String fileName, String texture) {
        this.fileName = fileName;
        this.texture = texture;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTexture() {
        return texture;
    }
}