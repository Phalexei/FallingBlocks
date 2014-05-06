package com.github.phalexei.fallingBlocks.Game.Objects;

import com.github.phalexei.fallingBlocks.Game.BlockGame;
import com.github.phalexei.fallingBlocks.Rendering.IRenderable;

import java.util.List;
import java.util.Stack;

public class GameGrid implements IRenderable {
    public static final int HEIGHT = 16;
    public static final int WIDTH = 10;

    private Block[][] grid;

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

    public boolean isFull(int x, int y) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            return grid[y][x] == null;
        } else { // checking for block out of grid
            return false;
        }
    }

    public void addBlocks(Shape shape) {
        List<Block> newBlocks = shape.getBlocks();

        for (Block b : newBlocks) {
            grid[b.getY()][b.getX()] = b;
        }
    }

    public void checkForLines(BlockGame game) {
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
            game.addLines(linesFound.size());

            do {
                deleteRow(linesFound.pop());
            } while(!linesFound.empty());
        }

    }

    private void deleteRow(Integer row) {
        for (; row < HEIGHT-1; row++) {
            grid[row] = grid[row+1];
            for(Block b : grid[row]) {
                if (b != null) {
                    b.move(Shape.Direction.DOWN);
                }
            }
        }
        grid[HEIGHT-1] = new Block[WIDTH];
    }
}
