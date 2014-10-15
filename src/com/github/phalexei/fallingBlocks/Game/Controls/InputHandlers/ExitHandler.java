package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class ExitHandler implements InputHandler {
    private static ExitHandler instance;

    private ExitHandler() {
    }

    public static ExitHandler getInstance() {
        if (instance == null) {
            instance = new ExitHandler();
        }
        return instance;
    }

    @Override
    public void action(FallingBlocksGame game) {
        game.exit();
    }
}
