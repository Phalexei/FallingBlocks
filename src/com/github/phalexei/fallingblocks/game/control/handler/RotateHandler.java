package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

public class RotateHandler implements InputHandler {
    private static RotateHandler instance = null;

    private RotateHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new RotateHandler();
        }
        return instance;
    }

    @Override
    public void action(FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.RUNNING) {
            game.rotate();
        }
    }
}
