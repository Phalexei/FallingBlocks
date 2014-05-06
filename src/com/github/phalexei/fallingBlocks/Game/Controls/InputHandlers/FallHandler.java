package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.BlockGame;

public class FallHandler implements InputHandler {
    private static FallHandler instance = null;
    private FallHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new FallHandler();
        }
        return instance;
    }

    @Override
    public void action(BlockGame game) {
        game.tryFall();
    }
}
