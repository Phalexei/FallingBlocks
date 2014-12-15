package com.github.phalexei.fallingblocks.rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

//saucecode's text rendering in opengl 1.1
//edited by kadence.
//adapted by Phalexei.

public class Text {

    private final TrueTypeFont font;
    private final TrueTypeFont boldFont;

    public Text() throws IOException, FontFormatException {
        final InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/spaceage.ttf");

        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);

        awtFont = awtFont.deriveFont(12f); // set font size
        this.font = new TrueTypeFont(awtFont, false);

        awtFont = awtFont.deriveFont(16f); // set font size
        this.boldFont = new TrueTypeFont(awtFont, false);
    }

    public void drawString(final String s, final int x, final int y) {
        this.drawString(s, x, y, false, 1.0f);
    }

    public void drawString(final String s, final int x, final int y, final boolean bold) {
        this.drawString(s, x, y, bold, 1.0f);
    }

    public void drawString(final String s, final int x, final int y, final float opacity) {
        this.drawString(s, x, y, false, opacity);
    }

    public void drawString(final String s, final int x, final int y, final boolean bold, final float opacity) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // get current color
        final FloatBuffer colors = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_CURRENT_COLOR, colors);

        final Color color = new Color(colors.get(0), colors.get(1), colors.get(2), opacity);

        if (bold) {
            this.boldFont.drawString(x, y, s, color);
        } else {
            this.font.drawString(x, y, s, color);
        }
        GL11.glDisable(GL11.GL_BLEND);
    }
}
