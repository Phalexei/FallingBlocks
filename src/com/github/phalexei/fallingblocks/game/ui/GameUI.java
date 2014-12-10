package com.github.phalexei.fallingblocks.game.ui;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;
import com.github.phalexei.fallingblocks.game.Score;
import com.github.phalexei.fallingblocks.game.object.Shape;
import com.github.phalexei.fallingblocks.game.object.shape.*;
import com.github.phalexei.fallingblocks.rendering.Renderable;
import com.github.phalexei.fallingblocks.rendering.Text;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameUI extends Renderable {

    private final List<Map.Entry<Integer, Float>> pointsToAdd, toRemove; // points/time
    private final FallingBlocksGame game;
    private char nextShapeType = '0';
    private Shape nextShape;

    private Texture inputsTexture;
    private Text text;

    public GameUI(FallingBlocksGame game) {
        pointsToAdd = new ArrayList<>();
        this.game = game;
        toRemove = new ArrayList<>();

        text = new Text();

        try {
            inputsTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/controls/WhiteSmallNew.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        inputsTexture.release();
    }

    @Override
    public void render(int tick) {
        switch (game.getState()) {
            case START:
                GL11.glColor3f(1.0f, 0.0f, 0.2f);
                text.drawString("FALLING BLOCKS", 75, 180);
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
                text.drawString("Press SPACE to start", 48, 200);
                drawScore(0);
                drawHUD();
                break;
            case RUNNING:
            case ERASING_LINES:
                drawNextShape(tick);
                drawScore(tick);
                drawHUD();
                break;
            case PAUSED:
                drawScore(0);
                greyOutGrid();
                GL11.glColor3f(1, 1, 1);
                text.drawString("GAME PAUSED", 75, 180, true);
                text.drawString("Press P to continue", 48, 200);
                drawHUD();
                break;
            case OVER:
                drawHUD();
                greyOutGrid();
                GL11.glColor3f(1, 1, 1);
                text.drawString("GAME OVER", 75, 80, true);
                text.drawString("Press R to restart", 48, 100);
                drawScore(0);
                drawHighScore();
                drawHUD();
                break;
            case EXITING:
                drawCredits();
                break;
        }
    }

    private void drawCredits() {
        GL11.glColor3f(1.0f, 0.0f, 0.2f);
        text.drawString("FALLING BLOCKS", 130, 180, true);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        text.drawString("A GAME BY PHALEXEI", 115, 200);
        text.drawString("THANK YOU FOR PLAYING", 103, 220);
    }

    private void drawHighScore() {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        text.drawString("HIGH SCORES", 73, 150);

        int y = 170;
        for (Score s : game.getHighScore()) {
            text.drawString(s.toString(), 105 - s.toString().length() * 2, y);
            y += 20;
        }
    }

    private void drawNextShape(int tick) {
        if (nextShapeType != game.getNextShapeType()) {
            nextShapeType = game.getNextShapeType();
            switch (nextShapeType) {
                case 'i':
                    nextShape = new IShape(300, 90, true);
                    break;
                case 'j':
                    nextShape = new JShape(300, 90, true);
                    break;
                case 'l':
                    nextShape = new LShape(300, 89, true);
                    break;
                case 's':
                    nextShape = new SShape(300, 89, true);
                    break;
                case 't':
                    nextShape = new TShape(300, 89, true);
                    break;
                case 'o':
                    nextShape = new OShape(300, 90, true);
                    break;
                case 'z':
                default:
                    nextShape = new ZShape(300, 89, true);
                    break;
            }
        }
        nextShape.render(tick);
    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.FOREGROUND;
    }

    private void drawScore(int tick) {

        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        // display current score
        text.drawString("SCORE:", 275, 320, true);
        text.drawString(game.getScore().toString(), 275, 330);

        // display lines
        text.drawString("LINES:", 275, 290, true);
        text.drawString(Integer.toString(game.getLines()), 275, 300);

        // display level
        text.drawString("LEVEL:", 275, 260, true);
        text.drawString(Integer.toString(game.getLines() / 10), 275, 270);

        float offset;

        // draw score slowly descending and fading
        for (Map.Entry<Integer, Float> points : pointsToAdd) {
            offset = points.getValue();
            text.drawString(points.getKey().toString(), 275, (int) (340 + offset), 1.0f - (offset / 60));
            points.setValue(offset + tick / 120f);

            // schedule to clear from list when offscren
            if (points.getValue() > 70) {
                toRemove.add(points);
            }
        }

        // actually clear list
        if (toRemove.size() > 0) {
            for (Map.Entry<Integer, Float> e : toRemove) {
                pointsToAdd.remove(e);
            }
            toRemove.clear();
        }
    }

    private void greyOutGrid() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 0.5f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2i(0, 0);
            GL11.glVertex2i(0, 400);
            GL11.glVertex2i(250, 400);
            GL11.glVertex2i(250, 0);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void addScore(int points) {
        pointsToAdd.add(new AbstractMap.SimpleEntry<Integer, Float>(points, 0.0f));
    }

    private void drawHUD() {
        GL11.glLineWidth(2.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        // Draw line separating game area from HUD
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex2i(250, 0);
            GL11.glVertex2i(250, 400);
        }
        GL11.glEnd();

        // Draw square for showing next piece
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex2i(280, 100 - 50);
            GL11.glVertex2i(280 + 50, 100 - 50);
            GL11.glVertex2i(280 + 50, 100);
            GL11.glVertex2i(280, 100);
            GL11.glVertex2i(280, 100 - 50);
        }
        GL11.glEnd();
        GL11.glPointSize(1.0f);

        GL11.glColor3f(1.0f, 0.0f, 0.2f);
        text.drawString("FALLING BLOCKS", 252, 20);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        text.drawString("A game by\nPhalexei", 252, 30);

        drawInputs();
    }

    private void drawInputs() {
        // Draw input map
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, inputsTexture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2i(244, 245 - 128);

            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2i(244 + 128, 245 - 128);

            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2i(244 + 128, 245);

            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2i(244, 245);
        }
        GL11.glEnd();
        // Unbind texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void reset() {
        pointsToAdd.clear();
    }
}
