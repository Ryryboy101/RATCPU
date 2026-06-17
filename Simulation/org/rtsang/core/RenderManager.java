package org.rtsang.core;

import org.lwjgl.opengl.*;
import org.rtsang.core.entity.Entity;
import org.rtsang.core.entity.Model;
import org.rtsang.core.utils.Transformation;
import org.rtsang.core.utils.Utils;

import java.util.List;
import java.util.Map;

public class RenderManager {
    private WindowManager window;
    private ShaderManager shader;

    public RenderManager(){

    }

    public void setWindow(WindowManager window) {
        this.window = window;
    }

    public void init() throws Exception{

        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vert"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.frag"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
    }

    public void render(Map<Model, List<Entity>> entities, Camera camera) {
        clear();
        shader.bind();

        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", camera.getViewMatrix());
        shader.setUniform("textureSampler", 0);

        for (Model model : entities.keySet()) {
            GL30.glBindVertexArray(model.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());

            for (Entity entity : entities.get(model)) {
                shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
        }
        shader.unbind();
    }
    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
    }
    public void cleanup(){
        shader.cleanup();
    }
}
