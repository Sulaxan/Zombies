package me.sulaxan.zombies.util;

import lombok.Getter;

import java.awt.*;

@Getter
public abstract class GameState {

    private String name;

    /**
     * @param name The internal name of the state.
     */
    public GameState(String name) {
        this.name = name;
    }

    /**
     * Signifies a call for when the state is first started.
     */
    public abstract void onStart();

    /**
     * Signifies a call for when the state is ended.
     */
    public abstract void onEnd();

    /**
     * Signifies a call for when there is a tick.
     */
    public abstract void tick();

    /**
     * Signifies a call for when render is requested.
     * @param graphics The graphics used to render.
     */
    public abstract void render(Graphics graphics);
}
