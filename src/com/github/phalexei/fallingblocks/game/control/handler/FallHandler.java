package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

public class FallHandler implements InputHandler {
    private static FallHandler instance = null;

    private FallHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new FallHandler();
        }
        return instance;
    }

    @Override
    public void action(final FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.RUNNING) {
            game.tryFall();
        }
    }
}
