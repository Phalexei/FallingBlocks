package com.github.phalexei.fallingBlocks.Game;

import com.github.phalexei.fallingBlocks.Game.Objects.GameGrid;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;
import com.github.phalexei.fallingBlocks.IUpdatable;
import com.github.phalexei.fallingBlocks.Rendering.Renderer;

public class BlockGame implements IUpdatable {

    private int ticksSinceLastUpdate;
    private int difficulty;
    private int lines;
    private int score;
    private double diffCoef;       // multiplier, not increasing linearly
    private GameUI ui;
    private GameGrid grid;
    private Shape fallingShape;
    private Renderer renderer;
    private GameState gameState;

    public Integer getScore() {
        return score;
    }

    public enum GameState {
        RUNNING,
        START,
        PAUSED,
        OVER
    }

    public BlockGame(Renderer renderer) {
        if (renderer == null) {
            throw new IllegalArgumentException();
        }
        this.renderer = renderer;

        ticksSinceLastUpdate = 1000;
        difficulty = 0;
        fallingShape = null;
        gameState = GameState.RUNNING;

        ui = new GameUI(this);
        renderer.addRenderable(ui);

        grid = new GameGrid();
        renderer.addRenderable(grid);
    }

    @Override
    public void update(int tick) {
        if (gameState == GameState.RUNNING) {
                doGameLoop(tick);
        }
    }

    private void doGameLoop(int tick) {
        ticksSinceLastUpdate += tick;
        if (ticksSinceLastUpdate >= getTicksThreshold()) {
            ticksSinceLastUpdate = 0;

            if (fallingShape == null) {
                spawnNewShape();
            } else {
                tryFall();
            }
        }
    }

    private void spawnNewShape() {
        fallingShape = Shape.randomShape(GameGrid.WIDTH /2-1, GameGrid.HEIGHT -1);
        renderer.addRenderable(fallingShape);
    }

    // try and make the block fall. If it can't, add to grid and delete
    public void tryFall() {
        if (fallingShape != null) {
            if (fallingShape.canFall(grid)) {
                fallingShape.fall();
            } else {
                grid.addBlocks(fallingShape);
                grid.checkForLines(this);
                renderer.removeRenderable(fallingShape);
                fallingShape = null;
            }
        }
    }

    // gives time to wait between updates, decreases as level increases
    private int getTicksThreshold() {
        return (int) (1000 - (900*diffCoef));
    }

    private void levelup() {
        difficulty++;
        diffCoef = (Math.sqrt(difficulty) / Math.sqrt(difficulty+10));
    }

    public void moveLeft() {
        if (fallingShape != null && fallingShape.canMove(grid, Shape.Direction.LEFT)) {
            fallingShape.move(Shape.Direction.LEFT);
        }
    }

    public void moveRight() {
        if (fallingShape != null && fallingShape.canMove(grid, Shape.Direction.RIGHT)) {
            fallingShape.move(Shape.Direction.RIGHT);
        }
    }

    public void pause() {
        if (gameState == GameState.RUNNING) {
            gameState = GameState.PAUSED;
        } else if (gameState == GameState.PAUSED) {
            gameState = GameState.RUNNING;
        }
    }

    public void rotate() {
        if (fallingShape != null) {
            if (fallingShape.canRotate(grid)) {
                fallingShape.rotate();
            }
        }
    }

    public void addLines(int nbLines) {
        lines += nbLines;
        if (lines / 10 > difficulty) {
            levelup();
        }

        int points = (int) (Math.pow(2,nbLines-1)* 100);

        score += points;
        ui.addScore(points);
    }
}
