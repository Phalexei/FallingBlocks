package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.BlockGame;
import com.github.phalexei.fallingBlocks.Main;

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
    public void action(BlockGame game) {
        if (game.getState() == BlockGame.GameState.OVER || Main.DEBUG) {
            game.reset();
        }
    }
}
