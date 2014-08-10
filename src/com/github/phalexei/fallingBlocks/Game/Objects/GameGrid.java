package com.github.phalexei.fallingBlocks.Game.Objects;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;
import com.github.phalexei.fallingBlocks.Rendering.Renderable;

import java.util.List;
import java.util.Stack;

public class GameGrid extends Renderable {
    public static final int HEIGHT = 16;
    public static final int WIDTH = 10;

    private final Block[][] grid;

    public GameGrid() {
        grid = new Block[HEIGHT][WIDTH];
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

    public void checkForLines(FallingBlocksGame game) {
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
            game.addLines(linesFound);
        }
    }

    public void deleteRow(Integer row) {
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
        for (Integer row : nbLines) {
            for (Block b : grid[row]) {
                b.setErasing(true);
            }
        }
    }
}
