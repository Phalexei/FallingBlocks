package com.github.phalexei.fallingblocks.game.ui;

import com.github.phalexei.fallingblocks.game.FallingBlocksGame;
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

    public GameUI(FallingBlocksGame game) {
        pointsToAdd = new ArrayList<Map.Entry<Integer, Float>>();
        this.game = game;
        toRemove = new ArrayList<Map.Entry<Integer, Float>>();

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
                drawHUD();
                GL11.glColor3f(1.0f, 0.0f, 0.2f);
                Text.drawString("FALLING BLOCKS", 75, 220, true);
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
                Text.drawString("Press SPACE to start", 48, 200);
                drawScore(0);
                break;
            case RUNNING:
            case ERASING_LINES:
                drawHUD();
                drawNextShape(tick);
                drawScore(tick);
                break;
            case PAUSED:
                drawHUD();
                drawScore(0);
                greyOutGrid();
                GL11.glColor3f(1, 1, 1);
                Text.drawString("GAME PAUSED", 75, 220, true);
                Text.drawString("Press P to continue", 48, 200);
                break;
            case OVER:
                drawHUD();
                greyOutGrid();
                GL11.glColor3f(1, 1, 1);
                Text.drawString("GAME OVER", 75, 220, true);
                Text.drawString("Press R to restart", 48, 200);
                drawScore(0);
                break;
            case EXITING:
                drawCredits();
                break;
        }
    }

    private void drawCredits() {
        GL11.glColor3f(1.0f, 0.0f, 0.2f);
        Text.drawString("FALLING BLOCKS", 130, 220, true);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        Text.drawString("A GAME BY PHALEXEI", 115, 200);
        Text.drawString("THANK YOU FOR PLAYING", 103, 180);
    }

    private void drawNextShape(int tick) {
        if (nextShapeType != game.getNextShapeType()) {
            nextShapeType = game.getNextShapeType();
            switch (nextShapeType) {
                case 'i':
                    nextShape = new IShape(300, 310, true);
                    break;
                case 'j':
                    nextShape = new JShape(300, 310, true);
                    break;
                case 'l':
                    nextShape = new LShape(300, 311, true);
                    break;
                case 's':
                    nextShape = new SShape(300, 311, true);
                    break;
                case 't':
                    nextShape = new TShape(300, 311, true);
                    break;
                case 'o':
                    nextShape = new OShape(300, 310, true);
                    break;
                case 'z':
                default:
                    nextShape = new ZShape(300, 311, true);
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
        Text.drawString("SCORE:", 275, 80, true);
        Text.drawString(game.getScore().toString(), 275, 70);

        // display lines
        Text.drawString("LINES:", 275, 110, true);
        Text.drawString(Integer.toString(game.getLines()), 275, 100);

        // display level
        Text.drawString("LEVEL:", 275, 140, true);
        Text.drawString(Integer.toString(game.getLines() / 10), 275, 130);

        float offset;

        // draw score slowly descending and fading
        for (Map.Entry<Integer, Float> points : pointsToAdd) {
            offset = points.getValue();
            Text.drawString(points.getKey().toString(), 275, (int) (60 - offset), 1.0f - (offset / 60));
            points.setValue(offset + tick / 80f);

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
            GL11.glVertex2i(280, 300 + 50);
            GL11.glVertex2i(280 + 50, 300 + 50);
            GL11.glVertex2i(280 + 50, 300);
            GL11.glVertex2i(280, 300);
            GL11.glVertex2i(280, 300 + 50);
        }
        GL11.glEnd();
        GL11.glPointSize(1.0f);

        drawInputs();

        GL11.glColor3f(1.0f, 0.0f, 0.2f);
        Text.drawString("FALLING BLOCKS", 252, 380);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        Text.drawString("A game by\nPhalexei", 252, 370);
    }

    private void drawInputs() {
        // Draw input map
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Color.white.bind();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, inputsTexture.getTextureID());


        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2i(244, 155 + 128);
            //GL11.glVertex2f(100, 100 + inputsTexture.getTextureHeight());

            GL11.glTexCoord2f(1, 0);
            //GL11.glVertex2f(100 + inputsTexture.getTextureWidth(), 100 + inputsTexture.getTextureHeight());
            GL11.glVertex2i(244 + 128, 155 + 128);

            GL11.glTexCoord2f(1, 1);
            //GL11.glVertex2f(100 + inputsTexture.getTextureWidth(), 100);
            GL11.glVertex2i(244 + 128, 155);

            GL11.glTexCoord2f(0, 1);
            //GL11.glVertex2f(100, 100);
            GL11.glVertex2i(244, 155);
        }
        GL11.glEnd();
        // Unbind texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void reset() {
        pointsToAdd.clear();
    }
}
