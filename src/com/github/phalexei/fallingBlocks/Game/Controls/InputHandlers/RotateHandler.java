package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class RotateHandler implements InputHandler {
    private static RotateHandler instance = null;
    private RotateHandler(){
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
