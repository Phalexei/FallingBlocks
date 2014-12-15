package com.github.phalexei.fallingblocks.game.object;

import com.github.phalexei.fallingblocks.game.object.shape.*;
import com.github.phalexei.fallingblocks.rendering.Renderable;

import java.util.List;
import java.util.Random;

public abstract class Shape extends Renderable {

    protected final static float T_RED = 1f;      // red
    protected final static float T_GREEN = 0f;
    protected final static float T_BLUE = 0f;
    protected final static float I_RED = 1f;      // yellow
    protected final static float I_GREEN = 1f;
    protected final static float I_BLUE = 0f;
    protected final static float Z_RED = 0f;      // green
    protected final static float Z_GREEN = 1f;
    protected final static float Z_BLUE = 0f;
    protected final static float S_RED = 0f;      // cyan
    protected final static float S_GREEN = 1f;
    protected final static float S_BLUE = 1f;
    protected final static float L_RED = 0f;      // blue
    protected final static float L_GREEN = 0f;
    protected final static float L_BLUE = 1f;
    protected final static float J_RED = 1f;      // pink
    protected final static float J_GREEN = 0f;
    protected final static float J_BLUE = 1f;
    protected final static float O_RED = 0.5f;    // grey
    protected final static float O_GREEN = 0.5f;
    protected final static float O_BLUE = 0.5f;
    private static final Random random = new Random();
    private static char nextShapeType = getNextShapeType();
    protected final boolean preview;
    protected Orientation orientation;
    protected int x, y;
    protected List<Block> blocks;

    protected Shape(int x, int y, boolean preview) {
        this.x = x;
        this.y = y;
        this.orientation = Orientation.DOWN;
        this.blocks = this.getNewBlocks(preview);
        this.preview = preview;
    }

    protected Shape(int x, int y) {
        this(x, y, false);
    }

    // returns a new random shape
    public static Shape randomShape(int x, int y) {
        Shape ret;
        switch (nextShapeType) {
            case 'i':
                ret = new IShape(x, y);
                break;
            case 'j':
                ret = new JShape(x, y);
                break;
            case 'l':
                ret = new LShape(x, y);
                break;
            case 's':
                ret = new SShape(x, y);
                break;
            case 't':
                ret = new TShape(x, y);
                break;
            case 'o':
                ret = new OShape(x, y);
                break;
            case 'z':
            default:
                ret = new ZShape(x, y);
                break;
        }
        nextShapeType = rollNextShapeType();
        return ret;
    }

    private static char rollNextShapeType() {
        int choice = random.nextInt(7);
        switch (choice) {
            case 0:
                return 'i';
            case 1:
                return 'j';
            case 2:
                return 'l';
            case 3:
                return 's';
            case 4:
                return 't';
            case 5:
                return 'o';
            case 6:
            default:
                return 'z';
        }
    }

    public static char getNextShapeType() {
        return nextShapeType;
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public boolean collides(GameGrid grid) {
        for (Block b : this.blocks) {
            if (b.collides(grid)) {
                return true;
            }
        }
        return false;
    }

    public boolean canRotate(GameGrid grid) {
        boolean canRotate = true;
        Orientation tmp = this.orientation;
        this.orientation = this.getNextOrientation();
        for (Block b : this.getNewBlocks(this.preview)) {
            canRotate &= grid.isEmpty(b.getX(), b.getY());
        }
        this.orientation = tmp;
        return canRotate;
    }

    public final void rotate() {
        this.orientation = this.getNextOrientation();
        this.blocks = this.getNewBlocks(this.preview);
    }

    protected final Orientation getNextOrientation() {
        return Orientation.values()[(this.orientation.ordinal() + 1) % Orientation.values().length];
    }

    protected abstract List<Block> getNewBlocks(boolean preview);

    public void move(Direction dir) {
        switch (dir) {
            case DOWN:
                this.y--;
                break;
            case LEFT:
                this.x--;
                break;
            case RIGHT:
                this.x++;
                break;
        }
        for (Block b : this.blocks) {
            b.move(dir);
        }
    }

    public boolean canMove(GameGrid grid, Direction direction) {
        boolean canMove = true;
        for (Block b : this.blocks) {
            canMove &= b.canMove(grid, direction);
        }
        return canMove;
    }

    public void fall() {
        this.move(Direction.DOWN);
    }

    public boolean canFall(GameGrid grid) {
        return this.canMove(grid, Direction.DOWN);
    }

    public void render(int tick) {
        for (Block b : this.blocks) {
            b.render(tick);
        }
    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.MIDDLE;
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
}
