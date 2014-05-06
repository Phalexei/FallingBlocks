package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class IShape extends Shape {

    public IShape(int x, int y) {
        super(x, y);
    }

    @Override
    protected List<Block> getNewBlocks() {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (orientation) {
            case DOWN:
            case UP:
                newBlocks.add(new Block(x, y, I_RED, I_GREEN, I_BLUE));
                newBlocks.add(new Block(x, y - 1, I_RED, I_GREEN, I_BLUE));
                newBlocks.add(new Block(x, y - 2, I_RED, I_GREEN, I_BLUE));
                newBlocks.add(new Block(x, y - 3, I_RED, I_GREEN, I_BLUE));
                break;
            case LEFT:
            case RIGHT:
                newBlocks.add(new Block(x, y, I_RED, I_GREEN, I_BLUE));
                newBlocks.add(new Block(x - 1, y, I_RED, I_GREEN, I_BLUE));
                newBlocks.add(new Block(x + 1, y, I_RED, I_GREEN, I_BLUE));
                newBlocks.add(new Block(x + 2, y, I_RED, I_GREEN, I_BLUE));
                break;
        }
        return newBlocks;
    }
}
