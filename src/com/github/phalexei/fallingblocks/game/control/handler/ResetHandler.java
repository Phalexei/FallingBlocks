package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

public class ResetHandler implements InputHandler {
    private static ResetHandler instance;

    private ResetHandler() {
    }

    public static ResetHandler getInstance() {
        if (instance == null) {
            instance = new ResetHandler();
        }
        return instance;
    }

    @Override
    public void action(final FallingBlocksGame game) {
        game.reset();
    }
}
