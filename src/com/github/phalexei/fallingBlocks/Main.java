package com.github.phalexei.fallingBlocks;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;
import com.github.phalexei.fallingBlocks.Rendering.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private long lastFrame;
    private static final int FPS =60;

    public static void main(String[] args){
        new Main();
    }

    private Main(){
        try {
            int delta = getDelta();

            List<IUpdatable> updatables = new ArrayList<IUpdatable>();

            // init OpenGL
            Renderer renderer = new Renderer();

            // init game
            FallingBlocksGame game = new FallingBlocksGame(renderer, updatables);

            // main game loop
            while (!Display.isCloseRequested() && !game.isCloseRequested()) {

                if (!Display.isActive() && game.getState() == FallingBlocksGame.GameState.RUNNING) { // window lost focus
                    game.pause();
                }

                for (IUpdatable u : updatables) {
                    u.update(delta);
                }

                Display.update();
                Display.sync(FPS);
                delta = getDelta();
            }

            game.close();
            Display.destroy();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private int getDelta() {
        long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

}