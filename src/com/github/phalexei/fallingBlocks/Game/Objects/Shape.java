package com.github.phalexei.fallingBlocks.Game.Objects;

import com.github.phalexei.fallingBlocks.Game.Objects.Shapes.*;
import com.github.phalexei.fallingBlocks.Rendering.Renderable;

import java.util.List;
import java.util.Random;

public abstract class Shape extends Renderable {

    protected Orientation orientation;
    protected int x,y;
    protected static float T_RED = 1f;      // red
    protected static float T_GREEN = 0f;
    protected static float T_BLUE = 0f;

    protected static float I_RED = 1f;      // yellow
    protected static float I_GREEN = 1f;
    protected static float I_BLUE = 0f;

    protected static float Z_RED = 0f;      // green
    protected static float Z_GREEN = 1f;
    protected static float Z_BLUE = 0f;

    protected static float S_RED = 0f;      // cyan
    protected static float S_GREEN = 1f;
    protected static float S_BLUE = 1f;

    protected static float L_RED = 0f;      // blue
    protected static float L_GREEN = 0f;
    protected static float L_BLUE = 1f;

    protected static float J_RED = 1f;      // pink
    protected static float J_GREEN = 0f;
    protected static float J_BLUE = 1f;

    protected static float O_RED = 0.5f;    // grey
    protected static float O_GREEN = 0.5f;
    protected static float O_BLUE = 0.5f;

    private static final Random random = new Random();

    public List<Block> getBlocks() {
        return blocks;
    }

    public enum Direction {
        LEFT,
        DOWN,
        RIGHT
    }

    public enum Orientation {
        DOWN,
        LEFT,
        UP,
        RIGHT
    }

    protected List<Block> blocks;

    public Shape(int x, int y){
        this.x = x;
        this.y = y;
        orientation = Orientation.DOWN;
        blocks = getNewBlocks();
    }

    public boolean collides(GameGrid grid) {
        for (Block b : blocks) {
            if (b.collides(grid)) {
                return true;
            }
        }
        return false;
    }

    public boolean canRotate(GameGrid grid) {
        boolean canRotate = true;
        Orientation tmp = orientation;
        orientation = getNextOrientation();
        for (Block b : getNewBlocks()) {
            canRotate &= grid.isEmpty(b.getX(), b.getY());
        }
        orientation = tmp;
        return canRotate;
    }

    public final void rotate() {
        orientation = getNextOrientation();
        blocks = getNewBlocks();
    }

    protected final Orientation getNextOrientation() {
        return Orientation.values()[(orientation.ordinal()+1)%Orientation.values().length];
    }

    protected abstract List<Block> getNewBlocks();

    public void move(Direction dir) {
        switch (dir) {
            case DOWN:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
        for (Block b : blocks) {
            b.move(dir);
        }
    }

    public boolean canMove(GameGrid grid, Direction direction) {
        boolean canMove = true;
        for (Block b : blocks) {
            canMove &= b.canMove(grid, direction);
        }
        return canMove;
    }

    public void fall() {
        move(Direction.DOWN);
    }

    public boolean canFall(GameGrid grid) {
        return canMove(grid, Direction.DOWN);
    }

    public void render(int tick) {
        for (Block b : blocks) {
            b.render(tick);
        }
    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.MIDDLE;
    }

    // returns a new random shape
    public static Shape randomShape(int x, int y) {
        int choice = random.nextInt(7);
        switch (choice) {
            case 0:
                return new IShape(x, y);
            case 1:
                return new JShape(x, y);
            case 2:
                return new LShape(x, y);
            case 3:
                return new SShape(x, y);
            case 4:
                return new TShape(x, y);
            case 5:
                return new OShape(x, y);
            case 6:
            default:
                return new ZShape(x, y);
        }
    }
}
