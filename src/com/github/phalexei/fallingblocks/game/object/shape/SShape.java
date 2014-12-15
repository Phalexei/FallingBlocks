package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class SShape extends Shape {

    public SShape(int x, int y) {
        super(x, y);
    }

    public SShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        List<Block> newBlocks = new ArrayList<>(4);

        switch (this.orientation) {
            case DOWN:
            case UP:
                newBlocks.add(new Block(this.x, this.y, S_RED, S_GREEN, S_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, S_RED, S_GREEN, S_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, S_RED, S_GREEN, S_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 2, S_RED, S_GREEN, S_BLUE, preview));
                break;
            case LEFT:
            case RIGHT:
                newBlocks.add(new Block(this.x + 1, this.y - 1, S_RED, S_GREEN, S_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, S_RED, S_GREEN, S_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, S_RED, S_GREEN, S_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 2, S_RED, S_GREEN, S_BLUE, preview));
                break;
        }
        return newBlocks;
    }

}
