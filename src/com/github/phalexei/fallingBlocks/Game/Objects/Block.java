package com.github.phalexei.fallingBlocks.Game.Objects;

import com.github.phalexei.fallingBlocks.Rendering.Renderable;
import org.lwjgl.opengl.GL11;

public class Block extends Renderable {
    private int x,y;
    private float red,green,blue;

    public Block(int x, int y, float red, float green, float blue) {
        this.x = x;
        this.y = y;
        this.red = red;
        this.green = green;
        this.blue = blue;
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
        GL11.glColor3f(red, green, blue);

        // draw quad
        //TODO : convert x,y to screen coords better

        // this is the square
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(x * 25, y * 25);
            GL11.glVertex2f(x * 25 + 25, y * 25);
            GL11.glVertex2f(x * 25 + 25, y * 25 + 25);
            GL11.glVertex2f(x * 25, y * 25 + 25);
        }
        GL11.glEnd();

        GL11.glColor3f(red - 0.2f, green - 0.2f, blue - 0.2f);
        GL11.glLineWidth(2f);

        // this is the outline, slightly darker
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex2f(x * 25, y * 25);
            GL11.glVertex2f(x * 25 + 25, y * 25);
            GL11.glVertex2f(x * 25 + 25, y * 25 + 25);
            GL11.glVertex2f(x * 25, y * 25 + 25);
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
}
