package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

public class MoveLeftHandler implements InputHandler {
    private static MoveLeftHandler instance = null;

    private MoveLeftHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new MoveLeftHandler();
        }
        return instance;
    }

    @Override
    public void action(final FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.RUNNING) {
            game.moveLeft();
        }
    }
}
