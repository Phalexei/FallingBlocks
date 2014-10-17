package com.github.phalexei.fallingblocks.game.object;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;
import com.github.phalexei.fallingblocks.rendering.Renderable;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Stack;

public class GameGrid extends Renderable {
    public static final int HEIGHT = 17;
    public static final int WIDTH = 10;

    private boolean showGrid;
    private final Block[][] grid;

    public GameGrid() {
        grid = new Block[HEIGHT][WIDTH];
        showGrid = false;
    }

    public void toggleShowGrid() {
        showGrid ^= true;
    }

    @Override
    public void render(int tick) {
        for (Block[] row : grid) {
            for (Block b : row) {
                if (b != null) {
                    b.render(tick);
                }
            }
        }

        if (showGrid) {
            drawGrid();
        }
    }

    private void drawGrid() {
        for (int col = 0; col < HEIGHT; col++) {
            for (int row = 0; row < WIDTH; row++) {
                drawGridPart(row, col);
            }
        }
    }

    private void drawGridPart(int row, int col) {

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.1f);

        int x, y, size;
        x = row * 25;
        y = col * 25;
        size = 25;
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex2f(x, y);
            GL11.glVertex2f(x + size, y);
            GL11.glVertex2f(x + size, y + size);
            GL11.glVertex2f(x, y + size);
        }
        GL11.glEnd();
    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.BACKGROUND;
    }

    public boolean isEmpty(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && grid[y][x] == null;
    }

    public void addBlocks(Shape shape) {
        List<Block> newBlocks = shape.getBlocks();

        for (Block b : newBlocks) {
            grid[b.getY()][b.getX()] = b;
        }
    }

    public boolean checkForLines(FallingBlocksGame game) {
        boolean ret = false;
        Stack<Integer> linesFound = new Stack<Integer>();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (grid[row][col] == null) {
                    break;
                } else if (col == WIDTH-1) {
                    linesFound.add(row);
                }
            }
        }
        if (!linesFound.empty()) {
            ret = true;
            game.addLines(linesFound);
        }
        return ret;
    }

    public void deleteRow(int row) {
        for (; row < HEIGHT-1; row++) {
            grid[row] = grid[row+1];
            for (Block b : grid[row]) {
                if (b != null) {
                    b.move(Shape.Direction.DOWN);
                }
            }
        }
        grid[HEIGHT-1] = new Block[WIDTH];
    }

    public void reset() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = null;
            }
        }
    }

    public void setErasing(Stack<Integer> nbLines) {
        for (int row : nbLines) {
            for (Block b : grid[row]) {
                b.setErasing(true);
            }
        }
    }
}
