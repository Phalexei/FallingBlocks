package com.github.phalexei.fallingblocks.game.control.handler;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;

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
    public void action(final FallingBlocksGame game) {
        game.exit();
    }
}
