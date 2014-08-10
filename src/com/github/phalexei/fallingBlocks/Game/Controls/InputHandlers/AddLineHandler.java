package com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers;

import com.github.phalexei.fallingBlocks.Game.BlockGame;

public class AddLineHandler implements InputHandler {

    private static AddLineHandler instance[] = null;
    private int nbLines;

    private AddLineHandler(int nbLines) {
        this.nbLines = nbLines;
    }

    public static InputHandler getInstance(int nbLines) {
        if (instance == null) {
            instance = new AddLineHandler[4];
        }
        if (instance[nbLines - 1] == null) {
            instance[nbLines - 1] = new AddLineHandler(nbLines);
        }
        return instance[nbLines - 1];
    }

    @Override
    public void action(BlockGame game) {
        game.addLines(nbLines);
    }
}
