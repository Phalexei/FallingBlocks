package com.github.phalexei.fallingblocks.rendering;

import com.github.phalexei.fallingblocks.IUpdatable;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer implements IUpdatable {

    private static final int FPS = 60;
    private final List<Renderable> renderables;

    public Renderer() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(740, 800));
        Display.setTitle("Falling Blocks");
        Display.create();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 370, 400, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        this.renderables = new ArrayList<>();
    }

    public void update(final int tick) {
        // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (final Renderable r : this.renderables) {
            r.render(tick);
        }

        Display.update();
        Display.sync(FPS);
    }

    public void addRenderable(final Renderable renderable) {
        this.renderables.add(renderable);
        Collections.sort(this.renderables);
    }

    public void removeRenderable(final Renderable renderable) {
        this.renderables.remove(renderable);
    }
}
