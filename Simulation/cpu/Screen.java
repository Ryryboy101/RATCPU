package org.rtsang.cpu;
import org.lwjgl.BufferUtils;
import org.rtsang.core.ObjectLoader;

import java.nio.ByteBuffer;

public class Screen {
    private int width;
    private int height;
    private ByteBuffer pixelBuffer;
    private int textureId;

    public Screen(int width, int height, ObjectLoader loader) {
        
        this.width = width;
        this.height = height;
        this.pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
        this.textureId = loader.createDynamicTexture(width, height);
    }

    public void setPixel(int x, int y, byte r, byte g, byte b, byte a) {
        if (x < 0 || x >= width || y < 0 || y >= height) return;

        int index = (y * width + x) * 4;
        pixelBuffer.put(index, r);
        pixelBuffer.put(index + 1, g);
        pixelBuffer.put(index + 2, b);
        pixelBuffer.put(index + 3, a);
    }

    public void pushBuffer(ObjectLoader loader) {
        loader.updateTexture(textureId, width, height, pixelBuffer);
    }

    public int getTextureId() {
        return textureId;
    }
}