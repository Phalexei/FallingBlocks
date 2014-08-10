package com.github.phalexei.fallingBlocks.Game.Objects.Shapes;

import com.github.phalexei.fallingBlocks.Game.Objects.Block;
import com.github.phalexei.fallingBlocks.Game.Objects.GameGrid;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class OShape extends Shape {
    private boolean blocksCalculated = false;

    public OShape(int x, int y) {
        super(x, y);
    }

    public OShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        if (blocksCalculated) {
            return blocks; //lol
        } else {
            List<Block> newBlocks = new ArrayList<Block>(4);

            newBlocks.add(new Block(x, y, O_RED, O_GREEN, O_BLUE, preview));
            newBlocks.add(new Block(x + 1, y, O_RED, O_GREEN, O_BLUE, preview));
            newBlocks.add(new Block(x, y - 1, O_RED, O_GREEN, O_BLUE, preview));
            newBlocks.add(new Block(x + 1, y - 1, O_RED, O_GREEN, O_BLUE, preview));

            blocksCalculated = true;
            return newBlocks;
        }
    }

    @Override
    public boolean canRotate(GameGrid grid) {
        return true;
    }
}
