package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class ResetHandler implements InputHandler {
    private static ResetHandler instance;

    private ResetHandler() {}

    public static ResetHandler getInstance() {
        if (instance == null) {
            instance = new ResetHandler();
        }
        return instance;
    }
    @Override
    public void action(FallingBlocksGame game) {
            game.reset();
    }
}
