package com.github.phalexei.fallingblocks.game;

import com.github.phalexei.fallingblocks.IUpdatable;
import com.github.phalexei.fallingblocks.file.Save;
import com.github.phalexei.fallingblocks.game.control.Input;
import com.github.phalexei.fallingblocks.game.object.GameGrid;
import com.github.phalexei.fallingblocks.game.object.Shape;
import com.github.phalexei.fallingblocks.game.ui.GameUI;
import com.github.phalexei.fallingblocks.rendering.Renderer;
import com.github.phalexei.fallingblocks.sound.Sound;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FallingBlocksGame implements IUpdatable {

    private final GameUI ui;
    private final GameGrid grid;
    private final Renderer renderer;
    private final Sound sound;
    private final Save save;
    private int ticksSinceLastUpdate;
    private int difficulty;
    private int lines;
    private int score;
    private double diffCoef;
    private Shape fallingShape;
    private ArrayList<Score> highScores;
    private GameState gameState;
    private GameState prevState;

    private int stateTimer;
    private Stack<Integer> linesToErase;

    public FallingBlocksGame(final Renderer renderer, final List<IUpdatable> updatables) throws IOException, FontFormatException {
        if (renderer == null) {
            throw new IllegalArgumentException();
        }

        this.renderer = renderer;
        updatables.add(this.renderer);

        updatables.add(this);

        // init controls
        updatables.add(new Input(this));

        // init sounds
        this.sound = new Sound();

        this.grid = new GameGrid();
        this.renderer.addRenderable(this.grid);

        this.ui = new GameUI(this);
        this.renderer.addRenderable(this.ui);

        this.save = new Save(Paths.get("save"));

        this.reset();
    }

    public Integer getScore() {
        return this.score;
    }

    public GameState getState() {
        return this.gameState;
    }

    public int getLines() {
        return this.lines;
    }

    public ArrayList<Score> getHighScore() {
        return this.highScores;
    }

    public void start() {
        this.gameState = GameState.RUNNING;
    }

    public void exit() {
        if (this.gameState != GameState.EXITING) {
            this.gameState = GameState.EXITING;
            this.stateTimer = 2000;
            this.renderer.removeRenderable(this.grid);
            this.renderer.removeRenderable(this.fallingShape);
        }
    }

    public boolean isCloseRequested() {
        return (this.gameState == GameState.EXITING && this.stateTimer <= 0);
    }

    public void close() {
        this.ui.close();
        this.sound.close();
    }

    public void reset() {
        this.gameState = GameState.START;
        this.ticksSinceLastUpdate = 1000;
        this.difficulty = 0;
        this.lines = 0;
        this.score = 0;
        this.diffCoef = 0;
        this.renderer.removeRenderable(this.fallingShape);
        this.fallingShape = null;
        this.gameState = GameState.START;
        this.prevState = null;
        this.sound.stopPlayingLineErase();

        this.grid.reset();
        this.ui.reset();
    }

    @Override
    public void update(int tick) {
        switch (this.gameState) {
            case RUNNING:
                this.doGameLoop(tick);
                break;
            case ERASING_LINES:
                this.stateTimer -= tick;
                if (this.stateTimer <= 0) {

                    while (this.linesToErase.size() > 0) {
                        this.grid.deleteRow(this.linesToErase.pop());
                    }
                    this.gameState = GameState.RUNNING;
                    this.sound.stopPlayingLineErase();
                }
                break;
            case EXITING:
                this.stateTimer -= tick;
                break;
        }
    }

    private void doGameLoop(int tick) {
        this.ticksSinceLastUpdate += tick;
        if (this.ticksSinceLastUpdate >= this.getTicksThreshold()) {
            this.ticksSinceLastUpdate = 0;

            if (this.fallingShape == null) {
                if (!this.spawnNewShape()) {
                    this.gameState = GameState.OVER;
                    this.sound.playGameOver();
                    if (this.score > 0) {
                        //TODO: get user name :'(
                        Score newScore = new Score("test", this.score);
                        if (this.save.addScore(newScore)) {
                        }
                    }
                    this.highScores = this.save.getHighScores();
                }
            } else {
                this.tryFall();
            }
        }
    }

    private boolean spawnNewShape() {
        boolean ret = false;
        this.fallingShape = Shape.randomShape(GameGrid.WIDTH / 2 - 1, GameGrid.HEIGHT - 1);
        this.renderer.addRenderable(this.fallingShape);
        if (!this.fallingShape.collides(this.grid)) {
            ret = true;
        }
        return ret;
    }

    // try and make the block fall. If it can't, add to grid and delete
    public void tryFall() {
        if (this.fallingShape != null) {
            if (this.fallingShape.canFall(this.grid)) {
                this.fallingShape.fall();
            } else {
                this.grid.addBlocks(this.fallingShape);
                if (!this.grid.checkForLines(this)) {
                    // do not play sound if we are deleting lines
                    this.sound.playBlockDrop();
                }
                this.renderer.removeRenderable(this.fallingShape);
                this.fallingShape = null;

                this.ticksSinceLastUpdate = 1000;
            }
        }
    }

    // gives time to wait between updates, decreases as level increases
    private int getTicksThreshold() {
        return (int) Math.max((1000 - (1000 * this.diffCoef)), 0);
    }

    private void levelup() {
        this.difficulty++;
        this.diffCoef = (Math.sqrt(this.difficulty) / Math.sqrt(this.difficulty + 10));
    }

    public void moveLeft() {
        if (this.fallingShape != null && this.fallingShape.canMove(this.grid, Shape.Direction.LEFT)) {
            this.fallingShape.move(Shape.Direction.LEFT);
        }
    }

    public void moveRight() {
        if (this.fallingShape != null && this.fallingShape.canMove(this.grid, Shape.Direction.RIGHT)) {
            this.fallingShape.move(Shape.Direction.RIGHT);
        }
    }

    public void pause() {
        if (this.gameState.isPausable()) {
            this.prevState = this.gameState;
            this.gameState = GameState.PAUSED;
        } else if (this.gameState == GameState.PAUSED) {
            this.gameState = this.prevState;
        }
    }

    public void rotate() {
        if (this.fallingShape != null) {
            if (this.fallingShape.canRotate(this.grid)) {
                this.fallingShape.rotate();
            }
        }
    }

    public void addLines(Stack<Integer> lines) {
        this.lines += lines.size();
        if (this.lines / 10 > this.difficulty) {
            this.levelup();
        }

        int points;
        switch (lines.size()) {
            case 1:
            default:
                points = 40 * (this.difficulty + 1);
                break;
            case 2:
                points = 100 * (this.difficulty + 1);
                break;
            case 3:
                points = 300 * (this.difficulty + 1);
                break;
            case 4:
                points = 1200 * (this.difficulty + 1);
                break;
        }

        this.score += points;
        this.ui.addScore(points);

        this.gameState = GameState.ERASING_LINES;
        this.stateTimer = 1000 + lines.size() * 250;
        this.linesToErase = lines;

        this.sound.startPlayingLineErase(lines.size() == 4);
        this.grid.setErasing(lines);
    }

    public char getNextShapeType() {
        return Shape.getNextShapeType();
    }

    public void toggleShowGrid() {
        this.grid.toggleShowGrid();
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
            return this.pausable;
        }
    }
}
