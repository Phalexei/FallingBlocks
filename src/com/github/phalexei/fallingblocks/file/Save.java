package com.github.phalexei.fallingblocks.file;

import com.github.phalexei.fallingblocks.game.Score;
import com.sun.javafx.collections.transformation.SortedList;

import javax.net.ssl.SSLContext;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Save {

    private static final String SEPARATOR = ",";
    private final Path where;
    private TreeSet<Score> highScores = new TreeSet<Score>();

    public Save(Path where) {
        this.where = where;
        try {
            List<String> content = Files.readAllLines(where, StandardCharsets.UTF_8);

            for (String s : content) {
                String[] split = s.split(SEPARATOR);
                highScores.add(new Score(split[0], Integer.getInteger(split[1])));
            }
        } catch (NoSuchFileException e) {
            //is ok
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-42);
        }
    }

    public SortedSet<Score> getHighScores() {
        return highScores;
    }

    public void addScore(Score score) {
        if (highScores.size() < 10) {
            highScores.add(score);
            write();
        } else if (score.getScore() > highScores.last().getScore()) {
            highScores.add(score);
            highScores.pollLast();
            write();
        }
    }

    private void write() {
        try (BufferedWriter w = Files.newBufferedWriter(where, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Score s : highScores) {
                w.write(s.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
