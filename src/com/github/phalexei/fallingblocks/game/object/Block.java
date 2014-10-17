package com.github.phalexei.fallingblocks.game.object;

import com.github.phalexei.fallingblocks.rendering.Renderable;
import org.lwjgl.opengl.GL11;

public class Block extends Renderable {
    private final boolean preview;
    private int x,y;
    private final float red,green,blue;
    private final int size;
    private final int ratio; // ratio from x,y coords to screen coords

    private boolean erasing;
    private boolean blinkingState;
    private int erasingTimer;

    public Block(int x, int y, float red, float green, float blue, boolean preview) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.preview = preview;
        this.erasing = false;

        if (preview) {
            this.size = 10;
            this.ratio = 10;
            this.x = 300 + (300 - x) * this.ratio;
            this.y = 310 + (310 - y) * this.ratio;
        } else {
            this.size = 25;
            this.ratio = 25;
            this.x = x;
            this.y = y;
        }
    }

    public void move(Shape.Direction dir) {
        switch (dir) {
            case DOWN:
                y--;
                break;
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
        }
    }

    @Override
    public void render(int tick) {
        // set the color of the quad (R,G,B,A)
        // if this block is being erased, blink it!
        if (erasing) {
            erasingTimer -= tick;
            if (erasingTimer <= 0) {
                blinkingState ^= true;
                erasingTimer = 250;
            }
            if (blinkingState) {
                GL11.glColor3f(red - 0.3f, green - 0.3f, blue - 0.3f);
            } else {
                GL11.glColor3f(red + 0.3f, green + 0.3f, blue + 0.3f);
            }
        } else {
            GL11.glColor3f(red, green, blue);
        }

        // compute screen coords only once per render ;-)
        float screenX = preview ? x /*+ 0.5f * ratio */: x * ratio;
        float screenY = preview ? y /*+ 0.5f * ratio */: y * ratio;

        // this is the square
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(screenX, screenY);
            GL11.glVertex2f(screenX + size, screenY);
            GL11.glVertex2f(screenX + size, screenY + size);
            GL11.glVertex2f(screenX, screenY + size);
        }
        GL11.glEnd();


        // this is the outline, slightly darker
        GL11.glColor3f(red - 0.2f, green - 0.2f, blue - 0.2f);
        GL11.glLineWidth(2f);

        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex2f(screenX, screenY);
            GL11.glVertex2f(screenX + size, screenY);
            GL11.glVertex2f(screenX + size, screenY + size);
            GL11.glVertex2f(screenX, screenY + size);
        }
        GL11.glEnd();
        GL11.glLineWidth(1f);
    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.MIDDLE;
    }

    public boolean canMove(GameGrid grid, Shape.Direction direction) {
        switch (direction) {
            case DOWN:
                return grid.isEmpty(x, y - 1);
            case LEFT:
                return grid.isEmpty(x - 1, y);
            case RIGHT:
                return grid.isEmpty(x + 1, y);
        }
        return false;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean collides(GameGrid grid) {
        return !grid.isEmpty(x, y);
    }

    public void setErasing(boolean erasing) {
        this.erasing = erasing;
        this.blinkingState = false;
        this.erasingTimer = 250;
    }
}
