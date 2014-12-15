package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

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
    public void action(final FallingBlocksGame game) {
        game.toggleShowGrid();
    }
}
