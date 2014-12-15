package com.github.phalexei.fallingblocks.sound;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Sound {

    private final Audio blockDrop;
    private final Audio gameOver;
    private final Audio lineErase;
    private final Audio tetris;
    private final Audio music;

    public Sound() throws IOException {
        this.blockDrop = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/blockDrop.wav"));
        this.gameOver = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/gameOver.wav"));
        this.lineErase = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/lineErase.wav"));
        this.tetris = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/tetris.wav"));
        this.music = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/Cipher2.wav"));

        this.music.playAsMusic(1.0f, 1.0f, true);
    }

    public void close() {
        AL.destroy();
    }

    public void playBlockDrop() {
        this.stopAllSounds();
        this.blockDrop.playAsSoundEffect(1.0f, 0.3f, false);
    }

    public void playGameOver() {
        this.stopAllSounds();
        this.gameOver.playAsSoundEffect(1.0f, 1.0f, false);
    }

    public void startPlayingLineErase(final boolean tetris) {
        this.stopAllSounds();
        if (tetris) {
            this.tetris.playAsSoundEffect(1.0f, 1.0f, true);
        } else {
            this.lineErase.playAsSoundEffect(1.0f, 1.0f, true);
        }
    }

    public void stopPlayingLineErase() {
        if (this.lineErase.isPlaying()) {
            this.lineErase.stop();
        }
        if (this.tetris.isPlaying()) {
            this.tetris.stop();
        }
    }

    private void stopAllSounds() {
        if (this.blockDrop.isPlaying()) {
            this.blockDrop.stop();
        }
        if (this.gameOver.isPlaying()) {
            this.gameOver.stop();
        }
        if (this.lineErase.isPlaying()) {
            this.lineErase.stop();
        }
    }

}
