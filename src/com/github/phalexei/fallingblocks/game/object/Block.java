package com.github.phalexei.fallingblocks.game.object;

import com.github.phalexei.fallingblocks.rendering.Renderable;
import org.lwjgl.opengl.GL11;

public class Block extends Renderable {
    private final boolean preview;
    private final float red, green, blue;
    private final int size;
    private final int ratio; // ratio from x,y coords to screen coords
    private int x, y;
    private boolean erasing;
    private boolean blinkingState;
    private int erasingTimer;

    public Block(final int x, final int y, final float red, final float green, final float blue, final boolean preview) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.preview = preview;
        this.erasing = false;

        if (preview) {
            this.size = 10;
            this.ratio = 10;
            this.x = 300 + (300 - x) * this.ratio;
            this.y = 300 + (90 - y) * this.ratio;
        } else {
            this.size = 25;
            this.ratio = 25;
            this.x = x;
            this.y = y;
        }
    }

    public void move(final Shape.Direction dir) {
        switch (dir) {
            case DOWN:
                this.y--;
                break;
            case RIGHT:
                this.x++;
                break;
            case LEFT:
                this.x--;
                break;
        }
    }

    @Override
    public void render(final int tick) {
        // set the color of the quad (R,G,B,A)
        // if this block is being erased, blink it!
        if (this.erasing) {
            this.erasingTimer -= tick;
            if (this.erasingTimer <= 0) {
                this.blinkingState ^= true;
                this.erasingTimer = 250;
            }
            if (this.blinkingState) {
                GL11.glColor3f(this.red - 0.3f, this.green - 0.3f, this.blue - 0.3f);
            } else {
                GL11.glColor3f(this.red + 0.3f, this.green + 0.3f, this.blue + 0.3f);
            }
        } else {
            GL11.glColor3f(this.red, this.green, this.blue);
        }

        // compute screen coords only once per render ;-)
        final float screenX = this.preview ? this.x /*+ 0.5f * ratio */ : this.x * this.ratio;
        final float screenY = 400 - (this.preview ? this.y /*+ 0.5f * ratio */ : this.y * this.ratio);

        // this is the square
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(screenX, screenY);
            GL11.glVertex2f(screenX + this.size, screenY);
            GL11.glVertex2f(screenX + this.size, screenY - this.size);
            GL11.glVertex2f(screenX, screenY - this.size);
        }
        GL11.glEnd();


        // this is the outline, slightly darker
        GL11.glColor3f(this.red - 0.2f, this.green - 0.2f, this.blue - 0.2f);
        GL11.glLineWidth(2f);

        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex2f(screenX, screenY);
            GL11.glVertex2f(screenX + this.size, screenY);
            GL11.glVertex2f(screenX + this.size, screenY - this.size);
            GL11.glVertex2f(screenX, screenY - this.size);
            GL11.glVertex2f(screenX, screenY);
        }
        GL11.glEnd();
        GL11.glLineWidth(1f);
    }

    @Override
    public ZIndex getZIndex() {
        return ZIndex.MIDDLE;
    }

    public boolean canMove(final GameGrid grid, final Shape.Direction direction) {
        switch (direction) {
            case DOWN:
                return grid.isEmpty(this.x, this.y - 1);
            case LEFT:
                return grid.isEmpty(this.x - 1, this.y);
            case RIGHT:
                return grid.isEmpty(this.x + 1, this.y);
        }
        return false;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public boolean collides(final GameGrid grid) {
        return !grid.isEmpty(this.x, this.y);
    }

    public void setErasing(final boolean erasing) {
        this.erasing = erasing;
        this.blinkingState = false;
        this.erasingTimer = 250;
    }
}
