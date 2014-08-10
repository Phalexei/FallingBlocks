package com.github.phalexei.fallingBlocks.Rendering;

public abstract class Renderable implements Comparable<Renderable> {

    public enum ZIndex {
        BACKGROUND,
        MIDDLE,
        FOREGROUND
    }

    public abstract void render(int tick);
    public abstract ZIndex getZIndex();

    public int compareTo(Renderable o) {
        return o.getZIndex().ordinal() > getZIndex().ordinal() ? -1 : o.getZIndex() == getZIndex() ? 0 : 1;
    }
}
