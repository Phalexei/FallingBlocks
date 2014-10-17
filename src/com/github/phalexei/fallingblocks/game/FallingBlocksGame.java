package com.github.phalexei.fallingblocks.game;

import com.github.phalexei.fallingblocks.file.Save;
import com.github.phalexei.fallingblocks.game.control.Input;
import com.github.phalexei.fallingblocks.game.object.GameGrid;
import com.github.phalexei.fallingblocks.game.object.Shape;
import com.github.phalexei.fallingblocks.game.ui.GameUI;
import com.github.phalexei.fallingblocks.IUpdatable;
import com.github.phalexei.fallingblocks.rendering.Renderer;
import com.github.phalexei.fallingblocks.sound.Sound;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
    private Sound sound;
    private Save save;
    private GameState gameState;
    private GameState prevState;

    private int stateTimer;
    private Stack<Integer> linesToErase;

    public Integer getScore() {
        return score;
    }

    public GameState getState() {
        return gameState;
    }


    public int getLines() {
        return lines;
    }

    public enum GameState {
        RUNNING,
        ERASING_LINES,
        START,
        PAUSED,
        OVER,
        EXITING;

        private boolean pausable;

        static {
            RUNNING.pausable = true;
            ERASING_LINES.pausable = true;
            START.pausable = false;
            PAUSED.pausable = false;
            OVER.pausable = false;
            EXITING.pausable = false;
        }

        public boolean isPausable() {
            return pausable;
        }
    }

    public FallingBlocksGame(Renderer renderer, List<IUpdatable> updatables) {
        if (renderer == null) {
            throw new IllegalArgumentException();
        }

        this.renderer = renderer;
        updatables.add(renderer);

        updatables.add(this);

        // init controls
        updatables.add(new Input(this));

        // init sounds
        sound = new Sound(this);

        grid = new GameGrid();
        renderer.addRenderable(grid);

        ui = new GameUI(this);
        renderer.addRenderable(ui);

        save = new Save(Paths.get("save"));

        reset();
    }

    public void start() {
        gameState = GameState.RUNNING;
    }

    public void exit() {
        if (gameState != GameState.EXITING) {
            gameState = GameState.EXITING;
            stateTimer = 2000;
            renderer.removeRenderable(grid);
            renderer.removeRenderable(fallingShape);
        }
    }

    public boolean isCloseRequested() {
        return (gameState == GameState.EXITING && stateTimer <= 0);
    }

    public void close() {
        ui.close();
        sound.close();
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
        sound.stopPlayingLineErase();

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
                stateTimer -= tick;
                if (stateTimer <= 0) {

                    while(linesToErase.size() > 0) {
                        grid.deleteRow(linesToErase.pop());
                    }
                    gameState = GameState.RUNNING;
                    sound.stopPlayingLineErase();
                }
                break;
            case EXITING:
                stateTimer -= tick;
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
                    sound.playGameOver();
                    if (score > 0) {
                        save.addScore(new Score("test", score));
                    }
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
                if (!grid.checkForLines(this)) {
                    // do not play sound if we are deleting lines
                    sound.playBlockDrop();
                }
                renderer.removeRenderable(fallingShape);
                fallingShape = null;

                ticksSinceLastUpdate = 1000;
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
        stateTimer = 1000 + lines.size() * 250;
        linesToErase = lines;

        sound.startPlayingLineErase(lines.size() == 4);
        grid.setErasing(lines);
    }

    public char getNextShapeType() {
        return Shape.getNextShapeType();
    }

    public void toggleShowGrid() {
        grid.toggleShowGrid();
    }
}
