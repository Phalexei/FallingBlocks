package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class JShape extends Shape {
    public JShape(int x, int y) {
        super(x, y);
        blocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE));
        blocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE));
        blocks.add(new Block(x - 1, y - 2, J_RED, J_GREEN, J_BLUE));
    }

    @Override
    protected List<Block> getNewBlocks() {
        List<Block> newBlocks = new ArrayList<Block>(4);

        switch (orientation) {
            case DOWN:
                newBlocks.add(new Block(x, y, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x - 1, y - 2, J_RED, J_GREEN, J_BLUE));
                break;
            case LEFT:
                newBlocks.add(new Block(x - 1, y, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x - 1, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x + 1, y - 1, J_RED, J_GREEN, J_BLUE));
                break;
            case UP:
                newBlocks.add(new Block(x + 1, y, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE));
                break;
            case RIGHT:
                newBlocks.add(new Block(x - 1, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x + 1, y - 1, J_RED, J_GREEN, J_BLUE));
                newBlocks.add(new Block(x + 1, y - 2, J_RED, J_GREEN, J_BLUE));
                break;
        }
        return newBlocks;
    }
}
