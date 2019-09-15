package me.sulaxan.zombies.game.object;

import lombok.NonNull;

import java.awt.*;

public abstract class GameObject {

    private ObjectBound bounds;

    public GameObject(@NonNull ObjectBound bounds) {
        this.bounds = bounds;
    }

    public final ObjectBound getBounds() {
        return bounds;
    }

    public abstract void onTick();

    public abstract void onRender(Graphics graphics);
}
