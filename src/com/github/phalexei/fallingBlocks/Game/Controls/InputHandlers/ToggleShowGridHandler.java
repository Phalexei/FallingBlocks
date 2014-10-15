package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;

public class ToggleShowGridHandler implements InputHandler {

    private static ToggleShowGridHandler instance;

    private ToggleShowGridHandler() {
    }

    public static ToggleShowGridHandler getInstance() {
        if (instance == null) {
            instance = new ToggleShowGridHandler();
        }
        return instance;
    }

    @Override
    public void action(FallingBlocksGame game) {
        game.toggleShowGrid();
    }
}
