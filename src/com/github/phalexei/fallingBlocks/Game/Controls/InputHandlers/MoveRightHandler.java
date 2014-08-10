package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class MoveRightHandler implements InputHandler {
    private static MoveRightHandler instance = null;
    private MoveRightHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
           instance = new MoveRightHandler();
        }
        return instance;
    }

    @Override
    public void action(FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.RUNNING) {
            game.moveRight();
        }
    }
}
