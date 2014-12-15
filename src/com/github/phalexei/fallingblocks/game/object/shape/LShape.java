package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class LShape extends Shape {
    public LShape(final int x, final int y) {
        super(x, y);
    }

    public LShape(final int x, final int y, final boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(final boolean preview) {
        final List<Block> newBlocks = new ArrayList<>(4);

        switch (this.orientation) {
            case DOWN:
                newBlocks.add(new Block(this.x, this.y, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 2, L_RED, L_GREEN, L_BLUE, preview));
                break;
            case LEFT:
                newBlocks.add(new Block(this.x - 1, this.y - 2, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                break;
            case UP:
                newBlocks.add(new Block(this.x - 1, this.y, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, L_RED, L_GREEN, L_BLUE, preview));
                break;
            case RIGHT:
                newBlocks.add(new Block(this.x - 1, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, L_RED, L_GREEN, L_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y, L_RED, L_GREEN, L_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
