package com.github.phalexei.fallingblocks.file;

import com.github.phalexei.fallingblocks.game.Score;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Save {

    private static final String SEPARATOR = ":";
    private final Path where;
    private ArrayList<Score> highScores;

    public Save(Path where) {
        this.where = where;
        highScores = new ArrayList<>();
        try {
            List<String> content = Files.readAllLines(where, StandardCharsets.UTF_8);

            for (String s : content) {
                String[] split = s.split(SEPARATOR);
                highScores.add(new Score(split[0], Integer.parseInt(split[1])));
            }
        } catch (NoSuchFileException e) {
            //is ok
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-42);
        }
    }

    public ArrayList<Score> getHighScores() {
        return highScores;
    }

    public boolean addScore(Score score) {
        int i = 0;
        while (i < highScores.size() && highScores.get(i).getScore() > score.getScore()) {
            i++;
        }
        if (i < highScores.size()) {
            highScores.add(i, score);
            write();
            if (highScores.size() > 10) {
                highScores.remove(10);
            }
            return true;
        }
        return false;
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
