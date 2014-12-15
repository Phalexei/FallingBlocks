package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class JShape extends Shape {
    public JShape(final int x, final int y) {
        super(x, y);
        this.blocks.add(new Block(x, y - 1, J_RED, J_GREEN, J_BLUE, this.preview));
        this.blocks.add(new Block(x, y - 2, J_RED, J_GREEN, J_BLUE, this.preview));
        this.blocks.add(new Block(x - 1, y - 2, J_RED, J_GREEN, J_BLUE, this.preview));
    }

    public JShape(final int x, final int y, final boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(final boolean preview) {
        final List<Block> newBlocks = new ArrayList<>(4);

        switch (this.orientation) {
            case DOWN:
                newBlocks.add(new Block(this.x, this.y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 2, J_RED, J_GREEN, J_BLUE, preview));
                break;
            case LEFT:
                newBlocks.add(new Block(this.x - 1, this.y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                break;
            case UP:
                newBlocks.add(new Block(this.x + 1, this.y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, J_RED, J_GREEN, J_BLUE, preview));
                break;
            case RIGHT:
                newBlocks.add(new Block(this.x - 1, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, J_RED, J_GREEN, J_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 2, J_RED, J_GREEN, J_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
