package com.github.phalexei.fallingblocks.game.control;

import com.github.phalexei.fallingblocks.IUpdatable;
import com.github.phalexei.fallingblocks.game.FallingBlocksGame;
import com.github.phalexei.fallingblocks.game.control.handler.*;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Input implements IUpdatable {
    private final Map<Integer, InputHandler> handlers;
    private final FallingBlocksGame game;

    public Input(FallingBlocksGame game) {
        this.game = game;
        Keyboard.enableRepeatEvents(true);

        this.handlers = new HashMap<>();
        this.handlers.put(Keyboard.KEY_UP, RotateHandler.getInstance());
        this.handlers.put(Keyboard.KEY_DOWN, FallHandler.getInstance());
        this.handlers.put(Keyboard.KEY_LEFT, MoveLeftHandler.getInstance());
        this.handlers.put(Keyboard.KEY_RIGHT, MoveRightHandler.getInstance());
        this.handlers.put(Keyboard.KEY_P, PauseHandler.getInstance());
        this.handlers.put(Keyboard.KEY_R, ResetHandler.getInstance());
        this.handlers.put(Keyboard.KEY_SPACE, StartHandler.getInstance());
        this.handlers.put(Keyboard.KEY_F1, ToggleShowGridHandler.getInstance());
        this.handlers.put(Keyboard.KEY_ESCAPE, ExitHandler.getInstance());
    }

    public void update(int tick) {
        InputHandler handler;
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) { // key was pressed
                handler = this.handlers.get(Keyboard.getEventKey());
                if (handler != null) {
                    handler.action(this.game);
                }
            }
        }
    }
}
