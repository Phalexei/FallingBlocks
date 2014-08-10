package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class ZShape extends Shape {
    public ZShape(int x, int y) {
        super(x, y);
    }

    public ZShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (getNextOrientation()) {
            case DOWN:
            case UP:
                newBlocks.add(new Block(x, y, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(x - 1, y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(x - 1, y - 2, Z_RED, Z_GREEN, Z_BLUE, preview));
                break;
            case LEFT:
            case RIGHT:
                newBlocks.add(new Block(x -1, y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(x, y - 2, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 2, Z_RED, Z_GREEN, Z_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
