package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class LShape extends Shape {
    public LShape(int x, int y) {
        super(x, y);
    }

    @Override
    protected List<Block> getNewBlocks() {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (orientation) {
            case DOWN:
                newBlocks.add(new Block(x, y, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y - 2, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x + 1, y - 2, L_RED, L_GREEN, L_BLUE));
                break;
            case LEFT:
                newBlocks.add(new Block(x - 1, y - 2, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x - 1, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x + 1, y - 1, L_RED, L_GREEN, L_BLUE));
                break;
            case UP:
                newBlocks.add(new Block(x - 1, y, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y - 2, L_RED, L_GREEN, L_BLUE));
                break;
            case RIGHT:
                newBlocks.add(new Block(x - 1, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x + 1, y - 1, L_RED, L_GREEN, L_BLUE));
                newBlocks.add(new Block(x + 1, y, L_RED, L_GREEN, L_BLUE));
                break;
        }
        return newBlocks;
    }
}
