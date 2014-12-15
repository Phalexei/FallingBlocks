package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

public class StartHandler implements InputHandler {
    private static StartHandler instance;

    private StartHandler() {
    }

    public static StartHandler getInstance() {
        if (instance == null) {
            instance = new StartHandler();
        }
        return instance;
    }

    @Override
    public void action(final FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.START) {
            game.start();
        }
    }
}
