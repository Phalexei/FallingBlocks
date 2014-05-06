package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class SShape extends Shape {

    public SShape(int x, int y) {
        super(x, y);
    }

    @Override
    protected List<Block> getNewBlocks() {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (orientation) {
            case DOWN:
            case UP:
                newBlocks.add(new Block(x, y, S_RED, S_GREEN, S_BLUE));
                newBlocks.add(new Block(x, y - 1, S_RED, S_GREEN, S_BLUE));
                newBlocks.add(new Block(x + 1, y - 1, S_RED, S_GREEN, S_BLUE));
                newBlocks.add(new Block(x + 1, y - 2, S_RED, S_GREEN, S_BLUE));
                break;
            case LEFT:
            case RIGHT:
                newBlocks.add(new Block(x + 1, y - 1, S_RED, S_GREEN, S_BLUE));
                newBlocks.add(new Block(x, y - 1, S_RED, S_GREEN, S_BLUE));
                newBlocks.add(new Block(x, y - 2, S_RED, S_GREEN, S_BLUE));
                newBlocks.add(new Block(x - 1, y - 2, S_RED, S_GREEN, S_BLUE));
                break;
        }
        return newBlocks;
    }

}
