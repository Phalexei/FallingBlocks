package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.GameGrid;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class OShape extends Shape {
    private boolean blocksCalculated = false;

    public OShape(final int x, final int y) {
        super(x, y);
    }

    public OShape(final int x, final int y, final boolean preview) {
        super(x, y, preview);
    }

    @Override
    public boolean canRotate(final GameGrid grid) {
        return true;
    }

    @Override
    protected List<Block> getNewBlocks(final boolean preview) {
        if (this.blocksCalculated) {
            return this.blocks; //lol
        } else {
            final List<Block> newBlocks = new ArrayList<>(4);

            newBlocks.add(new Block(this.x, this.y, O_RED, O_GREEN, O_BLUE, preview));
            newBlocks.add(new Block(this.x + 1, this.y, O_RED, O_GREEN, O_BLUE, preview));
            newBlocks.add(new Block(this.x, this.y - 1, O_RED, O_GREEN, O_BLUE, preview));
            newBlocks.add(new Block(this.x + 1, this.y - 1, O_RED, O_GREEN, O_BLUE, preview));

            this.blocksCalculated = true;
            return newBlocks;
        }
    }
}
