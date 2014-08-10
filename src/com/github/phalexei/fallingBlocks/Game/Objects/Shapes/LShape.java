package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class LShape extends Shape {
    public LShape(int x, int y) {
        super(x, y);
    }

    public LShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (orientation) {
            case DOWN:
                newBlocks.add(new Block(x, y, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y - 2, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 2, L_RED, L_GREEN, L_BLUE, preview));
                break;
            case LEFT:
                newBlocks.add(new Block(x - 1, y - 2, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x - 1, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                break;
            case UP:
                newBlocks.add(new Block(x - 1, y, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y - 2, L_RED, L_GREEN, L_BLUE, preview));
                break;
            case RIGHT:
                newBlocks.add(new Block(x - 1, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(x + 1, y, L_RED, L_GREEN, L_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
