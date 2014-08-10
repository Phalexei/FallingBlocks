package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class PauseHandler implements InputHandler {
    private static PauseHandler instance = null;
    private PauseHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new PauseHandler();
        }
        return instance;
    }

    @Override
    public void action(FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.RUNNING
                || game.getState() == FallingBlocksGame.GameState.PAUSED) {
            game.pause();
        }
    }
}
