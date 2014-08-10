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
    private double diffCoef;
    private GameUI ui;
    private GameGrid grid;
    private Shape fallingShape;
    private Renderer renderer;
    private GameState gameState;
    private GameState prevState;

    public Integer getScore() {
        return score;
    }

    public GameState getState() {
        return gameState;
    }

    public enum GameState {
        RUNNING,
        ERASING_LINE,
        START,
        PAUSED,
        OVER;

        private boolean pausable;

        static {
            RUNNING.pausable = true;
            ERASING_LINE.pausable = true;
            START.pausable = false;
            PAUSED.pausable = false;
            OVER.pausable = false;
        }

        boolean isPausable() {
            return pausable;
        }
    }

    public BlockGame(Renderer renderer) {
        if (renderer == null) {
            throw new IllegalArgumentException();
        }
        this.renderer = renderer;

        grid = new GameGrid();
        renderer.addRenderable(grid);

        ui = new GameUI(this);
        renderer.addRenderable(ui);

        reset();
    }

    public void start() {
        gameState = GameState.RUNNING;
    }

    public void reset() {
        gameState = GameState.START;
        ticksSinceLastUpdate = 1000;
        difficulty = 0;
        lines = 0;
        score = 0;
        diffCoef = 0;
        renderer.removeRenderable(fallingShape);
        fallingShape = null;
        gameState = GameState.START;
        prevState = null;

        grid.reset();
        ui.reset();
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
                if (!spawnNewShape()) {
                    gameState = GameState.OVER;
                }
            } else {
                tryFall();
            }
        }
    }

    private boolean spawnNewShape() {
        boolean ret = false;
        fallingShape = Shape.randomShape(GameGrid.WIDTH / 2 - 1, GameGrid.HEIGHT - 1);
        renderer.addRenderable(fallingShape);
        if (!fallingShape.collides(grid)) {
            ret = true;
        }
        return ret;
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
        return (int) Math.max((1000 - (1000 * diffCoef)), 0);
    }

    private void levelup() {
        difficulty++;
        diffCoef =  difficulty / 15;
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
        if (gameState.isPausable()) {
            prevState = gameState;
            gameState = GameState.PAUSED;
        } else if (gameState == GameState.PAUSED) {
            gameState = prevState;
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

        ticksSinceLastUpdate = 1000;
    }
}
