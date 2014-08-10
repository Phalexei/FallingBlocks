package com.github.phalexei.fallingBlocks.Game;

import com.github.phalexei.fallingBlocks.Rendering.Renderable;
import com.github.phalexei.fallingBlocks.Rendering.Text;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.List;

public class GameUI extends Renderable {

    private List<Map.Entry<Integer, Float>> pointsToAdd, toRemove; // points/time
    private BlockGame game;

    public GameUI(BlockGame game) {
        pointsToAdd = new ArrayList<Map.Entry<Integer, Float>>();
        this.game = game;
        toRemove = new ArrayList<Map.Entry<Integer, Float>>();
    }

    @Override
    public void render(int tick) {

        drawHUD();

        switch (game.getState()) {
            case START:
                GL11.glColor3f(1, 1, 1);
                Text.drawString("FALLING BLOCKS", 75, 220, true);
                Text.drawString("Press SPACE to start", 48, 200);
                break;
            case RUNNING:
                drawScore(tick);
                break;
            case PAUSED:
                greyOutGrid();
                GL11.glColor3f(1, 1, 1);
                Text.drawString("GAME PAUSE", 75, 220, true);
                Text.drawString("Press P to continue", 48, 200);
                break;
            case OVER:
                greyOutGrid();
                GL11.glColor3f(1, 1, 1);
                Text.drawString("GAME OVER", 75, 220, true);
                Text.drawString("Press R to restart", 48, 200);
                break;
        }

        Text.drawString("FALLING BLOCKS\nA game by\nPhalexei", 251, 380);

        Text.drawString("SCORE:", 275, 110, true);
        Text.drawString(game.getScore().toString(), 275, 100);

    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.FOREGROUND;
    }

    public void drawScore(int tick) {
        float offset;

        // draw score slowly descending and fading
        for (Map.Entry<Integer, Float> points : pointsToAdd) {
            offset = points.getValue();
            Text.drawString(points.getKey().toString(), 275, (int) (90 - offset), 1.0f / (offset / 8));
            points.setValue(offset + tick / 50f);

            // schedule to clear from list when offscren
            if (points.getValue() > 90) {
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
            GL11.glVertex2i(0, 0);
            GL11.glVertex2i(0, 400);
            GL11.glVertex2i(250, 400);
            GL11.glVertex2i(250, 0);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);

    }

    public void addScore(int points) {
        pointsToAdd.add(new AbstractMap.SimpleEntry<Integer, Float>(points, 0.0f));
    }

    private void drawHUD() {
        GL11.glLineWidth(2.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2i(250, 0);
            GL11.glVertex2i(250, 400);
        GL11.glEnd();
        GL11.glPointSize(1.0f);
    }

    public void reset() {
        pointsToAdd.clear();
    }
}
