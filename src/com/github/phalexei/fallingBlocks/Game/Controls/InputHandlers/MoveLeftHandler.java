package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.BlockGame;

public class MoveLeftHandler implements InputHandler {
    private static MoveLeftHandler instance = null;
    private MoveLeftHandler() {
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new MoveLeftHandler();
        }
        return instance;
    }

    @Override
    public void action(BlockGame game) {
        game.moveLeft();
    }
}
