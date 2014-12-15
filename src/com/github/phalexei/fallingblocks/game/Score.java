package com.github.phalexei.fallingblocks.game;

public class Score implements Comparable<Score> {

    private final int score;
    private final String name;

    public Score(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(final Score o) {
        return -Integer.compare(this.score, o.score);
    }

    @Override
    public boolean equals(final Object o) {
        return false;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.score;
    }

    public int getScore() {
        return this.score;
    }
}
