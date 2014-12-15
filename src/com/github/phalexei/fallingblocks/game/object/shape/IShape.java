package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class IShape extends Shape {

    public IShape(int x, int y) {
        super(x, y);
    }

    public IShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        List<Block> newBlocks = new ArrayList<>(4);

        switch (this.orientation) {
            case DOWN:
            case UP:
                newBlocks.add(new Block(this.x, this.y, I_RED, I_GREEN, I_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, I_RED, I_GREEN, I_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, I_RED, I_GREEN, I_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 3, I_RED, I_GREEN, I_BLUE, preview));
                break;
            case LEFT:
            case RIGHT:
                newBlocks.add(new Block(this.x, this.y, I_RED, I_GREEN, I_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y, I_RED, I_GREEN, I_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y, I_RED, I_GREEN, I_BLUE, preview));
                newBlocks.add(new Block(this.x + 2, this.y, I_RED, I_GREEN, I_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
