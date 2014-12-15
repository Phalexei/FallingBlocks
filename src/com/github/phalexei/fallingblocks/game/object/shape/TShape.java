package com.github.phalexei.fallingblocks.game.object.shape;

import com.github.phalexei.fallingblocks.game.object.Block;
import com.github.phalexei.fallingblocks.game.object.Shape;

import java.util.ArrayList;
import java.util.List;

public class TShape extends Shape {

    public TShape(int x, int y) {
        super(x, y);
    }

    public TShape(int x, int y, boolean preview) {
        super(x, y, preview);
    }

    @Override
    protected List<Block> getNewBlocks(boolean preview) {
        List<Block> newBlocks = new ArrayList<>(4);

        switch (this.orientation) {
            case DOWN:
                newBlocks.add(new Block(this.x - 1, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                break;
            case LEFT:
                newBlocks.add(new Block(this.x, this.y, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x - 1, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, T_RED, T_GREEN, T_BLUE, preview));
                break;
            case UP:
                newBlocks.add(new Block(this.x - 1, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                break;
            case RIGHT:
                newBlocks.add(new Block(this.x, this.y, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x + 1, this.y - 1, T_RED, T_GREEN, T_BLUE, preview));
                newBlocks.add(new Block(this.x, this.y - 2, T_RED, T_GREEN, T_BLUE, preview));
                break;
        }
        return newBlocks;
    }
}
