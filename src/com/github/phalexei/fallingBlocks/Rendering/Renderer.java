package com.github.phalexei.fallingBlocks.Rendering;

import com.github.phalexei.fallingBlocks.IUpdatable;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer implements IUpdatable {

    private List<Renderable> renderables;

    public Renderer() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(740, 800));
        Display.setTitle("Falling Blocks");
        Display.create();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 370, 0, 400, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        renderables = new ArrayList<Renderable>();
    }

    public void update(int tick) {
        // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (Renderable r : renderables)
            r.render(tick);
    }

    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
        Collections.sort(renderables);
    }

    public void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }
}
