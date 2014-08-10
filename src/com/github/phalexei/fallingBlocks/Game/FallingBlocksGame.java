package com.github.phalexei.fallingBlocks.Game;

import com.github.phalexei.fallingBlocks.Game.Objects.GameGrid;
import com.github.phalexei.fallingBlocks.Game.Objects.Shape;
import com.github.phalexei.fallingBlocks.Game.UI.GameUI;
import com.github.phalexei.fallingBlocks.IUpdatable;
import com.github.phalexei.fallingBlocks.Rendering.Renderer;

import java.util.Stack;

public class FallingBlocksGame implements IUpdatable {

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

    private int erasingLinesTimer;
    private Stack<Integer> linesToErase;

    public Integer getScore() {
        return score;
    }

    public GameState getState() {
        return gameState;
    }

    public void close() {
        ui.close();
    }

    public Integer getLines() {
        return lines;
    }

    public enum GameState {
        RUNNING,
        ERASING_LINES,
        START,
        PAUSED,
        OVER;

        private boolean pausable;

        static {
            RUNNING.pausable = true;
            ERASING_LINES.pausable = true;
            START.pausable = false;
            PAUSED.pausable = false;
            OVER.pausable = false;
        }

        boolean isPausable() {
            return pausable;
        }
    }

    public FallingBlocksGame(Renderer renderer) {
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
        switch (gameState) {
            case RUNNING:
                doGameLoop(tick);
                break;
            case ERASING_LINES:
                erasingLinesTimer -= tick;
                if (erasingLinesTimer <= 0) {

                    while(linesToErase.size() > 0) {
                        grid.deleteRow(linesToErase.pop());
                    }
                    gameState = GameState.RUNNING;
                }
                break;
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

    public void addLines(Stack<Integer> lines) {
        this.lines += lines.size();
        if (this.lines / 10 > difficulty) {
            levelup();
        }

        int points;
        switch (lines.size()) {
            case 1:
            default:
                points = 40 * (difficulty + 1);
                break;
            case 2:
                points = 100 * (difficulty + 1);
                break;
            case 3:
                points = 300 * (difficulty + 1);
                break;
            case 4:
                points = 1200 * (difficulty + 1);
                break;
        }

        score += points;
        ui.addScore(points);

        gameState = GameState.ERASING_LINES;
        erasingLinesTimer = 1000 + lines.size() * 250;
        linesToErase = lines;

        grid.setErasing(lines);

        ticksSinceLastUpdate = 1000;
    }

    public char getNextShapeType() {
        return Shape.getNextShapeType();
    }
}
