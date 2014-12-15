package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class ZShape extends Shape {
    public ZShape(final int x, final int y) {
        super(x, y);
    }

    public ZShape(final int x, final int y, final boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(final boolean preview) {
        final List<Block> newBlocks = new ArrayList<>(4);

        switch (this.getNextOrientation()) {
            case DOWN:
            case UP:
                newBlocks.add(new Block(this.x, this.y, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 2, Z_RED, Z_GREEN, Z_BLUE, preview));
                break;
            case LEFT:
            case RIGHT:
                newBlocks.add(new Block(this.x - 1, this.y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, Z_RED, Z_GREEN, Z_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 2, Z_RED, Z_GREEN, Z_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
