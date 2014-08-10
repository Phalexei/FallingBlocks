package com.github.phalexei.fallingBlocks.Game.Controls;

import com.github.phalexei.fallingBlocks.Game.FallingBlocksGame;
import com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers.*;
import com.github.phalexei.fallingBlocks.IUpdatable;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Input implements IUpdatable {
    private final Map<Integer, InputHandler> handlers;
    private final FallingBlocksGame game;

    public Input(FallingBlocksGame game){
        this.game = game;
        Keyboard.enableRepeatEvents(true);

        handlers = new HashMap<Integer, InputHandler>();
        handlers.put(Keyboard.KEY_UP, RotateHandler.getInstance());
        handlers.put(Keyboard.KEY_DOWN, FallHandler.getInstance());
        handlers.put(Keyboard.KEY_LEFT, MoveLeftHandler.getInstance());
        handlers.put(Keyboard.KEY_RIGHT, MoveRightHandler.getInstance());
        handlers.put(Keyboard.KEY_P, PauseHandler.getInstance());
        handlers.put(Keyboard.KEY_R, ResetHandler.getInstance());
        handlers.put(Keyboard.KEY_SPACE, StartHandler.getInstance());
    }

    public void update(int tick) {
        InputHandler handler;
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) { // key was pressed
                handler = handlers.get(Keyboard.getEventKey());
                if (handler != null) {
                    handler.action(game);
                }
            }
        }
    }
}
