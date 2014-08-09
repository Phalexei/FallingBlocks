package com.github.phalexei.fallingBlocks.Game;

import com.github.phalexei.fallingBlocks.Rendering.IRenderable;
import com.github.phalexei.fallingBlocks.Rendering.Text;

import java.util.*;
import java.util.List;

public class GameUI implements IRenderable {

    private List<Map.Entry<Integer, Integer>> pointsToAdd; // points/time
    private BlockGame game;

    public GameUI(BlockGame game) {
        pointsToAdd = new ArrayList<Map.Entry<Integer, Integer>>();
        this.game = game;
    }

    @Override
    public void render(int tick) {

        Text.drawString(game.getScore().toString(), 100, 100);

        for (Map.Entry<Integer, Integer> points : pointsToAdd) {
            // TODO: display incoming scores with a cool effect 8-)
        }
    }

    public void addScore(int points) {
        pointsToAdd.add(new AbstractMap.SimpleEntry<Integer, Integer>(points, 0));
    }
}
