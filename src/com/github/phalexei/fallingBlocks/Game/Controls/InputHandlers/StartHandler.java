package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class StartHandler implements InputHandler {
    private static StartHandler instance;

    private StartHandler() {}

    public static StartHandler getInstance() {
        if (instance == null) {
            instance = new StartHandler();
        }
        return instance;
    }
    @Override
    public void action(FallingBlocksGame game) {
        if (game.getState() == FallingBlocksGame.GameState.START) {
            game.start();
        }
    }
}
