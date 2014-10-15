package com.github.phalexei.fallingBlocks.Sound;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Sound {

    private final FallingBlocksGame game;
    private Audio blockDrop;
    private Audio gameOver;
    private Audio lineErase;
    private Audio tetris;
    private Audio music;

    public Sound(FallingBlocksGame game) {
        this.game = game;

        try {
            blockDrop = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/blockDrop.wav"));
            gameOver = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/gameOver.wav"));
            lineErase = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/lineErase.wav"));
            tetris = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/tetris.wav"));

            music = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/Cipher2.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        music.playAsMusic(1.0f, 1.0f, true);
    }

    public void close() {
        AL.destroy();
    }

    public void playBlockDrop() {
        stopAllSounds();
        blockDrop.playAsSoundEffect(1.0f, 0.3f, false);
    }

    public void playGameOver() {
        stopAllSounds();
        gameOver.playAsSoundEffect(1.0f, 1.0f, false);
    }

    public void startPlayingLineErase(boolean tetris) {
        stopAllSounds();
        if (tetris) {
            this.tetris.playAsSoundEffect(1.0f, 1.0f, true);
        } else {
            lineErase.playAsSoundEffect(1.0f, 1.0f, true);
        }
    }

    public void stopPlayingLineErase() {
        if (lineErase.isPlaying()) {
            lineErase.stop();
        }
        if (tetris.isPlaying()) {
            tetris.stop();
        }
    }

    private void stopAllSounds() {
        if (blockDrop.isPlaying()) {
            blockDrop.stop();
        }
        if (gameOver.isPlaying()) {
            gameOver.stop();
        }
        if (lineErase.isPlaying()) {
            lineErase.stop();
        }
    }

}
