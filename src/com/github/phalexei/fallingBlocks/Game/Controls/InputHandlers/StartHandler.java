package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.BlockGame;

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
    public void action(BlockGame game) {
        if (game.getState() == BlockGame.GameState.START) {
            game.start();
        }
    }
}
