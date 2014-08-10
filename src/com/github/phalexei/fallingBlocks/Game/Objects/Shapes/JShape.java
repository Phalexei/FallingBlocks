package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class JShape extends Shape {
    public JShape(int x, int y) {
        super(x, y);
        blocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE, preview));
        blocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE, preview));
        blocks.add(new Block(x - 1, y - 2, J_RED, J_GREEN, J_BLUE, preview));
    }

    public JShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (orientation) {
            case DOWN:
                newBlocks.add(new Block(x, y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x - 1, y - 2, J_RED, J_GREEN, J_BLUE, preview));
                break;
            case LEFT:
                newBlocks.add(new Block(x - 1, y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x - 1, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                break;
            case UP:
                newBlocks.add(new Block(x + 1, y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE, preview));
                break;
            case RIGHT:
                newBlocks.add(new Block(x - 1, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(x + 1, y - 2, J_RED, J_GREEN, J_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
