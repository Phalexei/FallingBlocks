package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

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
    public void action(FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.RUNNING) {
            game.tryFall();
        }
    }
}
