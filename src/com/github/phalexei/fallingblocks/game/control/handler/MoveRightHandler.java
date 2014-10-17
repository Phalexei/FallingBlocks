package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

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
