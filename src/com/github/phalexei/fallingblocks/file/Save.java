package com.github.phalexei.fallingblocks.file;

import com.github.phalexei.fallingblocks.game.Score;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Save {

    private static final String SEPARATOR = ":";
    private final Path where;
    private ArrayList<Score> highScores;

    public Save(Path where) {
        this.where = where;
        this.highScores = new ArrayList<>();
        try {
            List<String> content = Files.readAllLines(where, StandardCharsets.UTF_8);

            for (String s : content) {
                String[] split = s.split(SEPARATOR);
                this.highScores.add(new Score(split[0], Integer.parseInt(split[1])));
            }
        } catch (NoSuchFileException e) {
            //is ok
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-42);
        }
    }

    public ArrayList<Score> getHighScores() {
        return this.highScores;
    }

    public boolean addScore(Score score) {
        int i = 0;
        while (i < this.highScores.size() && this.highScores.get(i).getScore() > score.getScore()) {
            i++;
        }
        if (i < this.highScores.size()) {
            this.highScores.add(i, score);
            this.write();
            if (this.highScores.size() > 10) {
                this.highScores.remove(10);
            }
            return true;
        }
        return false;
    }

    private void write() {
        try (BufferedWriter w = Files.newBufferedWriter(this.where, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Score s : this.highScores) {
                w.write(s.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
