package com.github.phalexei.fallingblocks.game;

public class Score implements Comparable<Score> {

    private final int score;
    private final String name;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Score o) {
        return -Integer.compare(this.score, o.score);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + ":" + score;
    }
}
