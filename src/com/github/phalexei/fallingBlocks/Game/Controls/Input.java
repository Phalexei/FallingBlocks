package com.github.phalexei.fallingBlocks.Game.Controls;

import com.github.phalexei.fallingBlocks.Game.BlockGame;
import com.github.phalexei.fallingBlocks.Game.Controls.InputHandlers.*;
import com.github.phalexei.fallingBlocks.IUpdatable;
import com.github.phalexei.fallingBlocks.Main;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Input implements IUpdatable {
    private Map<Integer, InputHandler> handlers;
    private BlockGame game;

    public Input(BlockGame game){
        this.game = game;
        Keyboard.enableRepeatEvents(true);

        handlers = new HashMap<Integer, InputHandler>();
        handlers.put(Keyboard.KEY_UP, RotateHandler.getInstance());
        handlers.put(Keyboard.KEY_DOWN, FallHandler.getInstance());
        handlers.put(Keyboard.KEY_LEFT, MoveLeftHandler.getInstance());
        handlers.put(Keyboard.KEY_RIGHT, MoveRightHandler.getInstance());
        handlers.put(Keyboard.KEY_P, PauseHandler.getInstance());
        handlers.put(Keyboard.KEY_RETURN, PauseHandler.getInstance());
        handlers.put(Keyboard.KEY_R, ResetHandler.getInstance());
        handlers.put(Keyboard.KEY_SPACE, StartHandler.getInstance());

        if (Main.DEBUG) {
            handlers.put(Keyboard.KEY_NUMPAD1, AddLineHandler.getInstance(1));
            handlers.put(Keyboard.KEY_NUMPAD2, AddLineHandler.getInstance(2));
            handlers.put(Keyboard.KEY_NUMPAD3, AddLineHandler.getInstance(3));
            handlers.put(Keyboard.KEY_NUMPAD4, AddLineHandler.getInstance(4));
        }
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
