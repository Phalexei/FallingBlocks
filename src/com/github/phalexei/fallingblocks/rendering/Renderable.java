package com.github.phalexei.fallingblocks.rendering;

public abstract class Renderable implements Comparable<Renderable> {

    public abstract void render(int tick);

    protected abstract ZIndex getZIndex();

    public int compareTo(Renderable o) {
        return o.getZIndex().ordinal() > this.getZIndex().ordinal() ? -1 : o.getZIndex() == this.getZIndex() ? 0 : 1;
    }

    public enum ZIndex {
        BACKGROUND,
        MIDDLE,
        FOREGROUND
    }
}
