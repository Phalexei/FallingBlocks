package com.github.phalexei.fallingblocks;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;
import com.github.phalexei.fallingblocks.rendering.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private long lastFrame;

    private Main() {
        try {
            int delta = this.getDelta();

            final List<IUpdatable> updatables = new ArrayList<>();

            // init OpenGL
            final Renderer renderer = new Renderer();
            updatables.add(renderer);

            // init game
            final FallingBlocksGame game = new FallingBlocksGame(renderer, updatables);

            // main game loop
            while (!Display.isCloseRequested() && !game.isCloseRequested()) {

                if (!Display.isActive() && game.getState() == FallingBlocksGame.GameState.RUNNING) { // window lost focus
                    game.pause();
                }

                for (final IUpdatable u : updatables) {
                    u.update(delta);
                }

                delta = this.getDelta();
            }

            game.close();
            Display.destroy();
        } catch (LWJGLException | IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        new Main();
    }

    private int getDelta() {
        final long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        final int delta = (int) (time - this.lastFrame);
        this.lastFrame = time;
        return delta;
    }

}