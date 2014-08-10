package com.github.phalexei.fallingBlocks;

import com.github.phalexei.fallingBlocks.Game.BlockGame;
import com.github.phalexei.fallingBlocks.Game.Controls.Input;
import com.github.phalexei.fallingBlocks.Rendering.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private long lastFrame;
    private int fps=60;
    private List<IUpdatable> updatables;

    public static final boolean DEBUG = true;

    public static void main(String[] args){
        System.out.println("Hello wrold!");
        new Main();
        System.out.println("Goodbye wrold!");
    }

    public Main(){
        try {
            int delta = getDelta();
            fps = 60;

            updatables = new ArrayList<IUpdatable>();
            // init OpenGL
            Renderer renderer = new Renderer();
            updatables.add(renderer);

            // init game
            BlockGame game = new BlockGame(renderer);
            updatables.add(game);

            // init controls
            updatables.add(new Input(game));

            // main game loop
            while (!Display.isCloseRequested()) {

                if (!Display.isActive() && game.getState() == BlockGame.GameState.RUNNING) { // window lost focus
                    game.pause();
                }

                for (IUpdatable u : updatables) {
                    u.update(delta);
                }

                Display.update();
                Display.sync(fps);
                delta = getDelta();
            }

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