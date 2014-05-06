package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.BlockGame;

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
    public void action(BlockGame game) {
        game.rotate();
    }
}
