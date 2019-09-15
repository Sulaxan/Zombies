package me.sulaxan.zombies.game.object.entity.impl;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.ObjectBound;
import me.sulaxan.zombies.game.object.entity.Collidable;
import me.sulaxan.zombies.game.object.entity.Entity;
import me.sulaxan.zombies.game.object.entity.EntityType;

import java.awt.*;

public class Barrier extends Entity implements Collidable {

    private Color color;

    public Barrier(Color color) {
        super("Barrier", EntityType.BARRIER, 0, 0, new ObjectBound(Zombies.SCALE, Zombies.SCALE));
        this.color = color;
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics g) {
        g.setColor(color);
        g.drawRect(getLocation().getX(), getLocation().getY(), Zombies.SCALE, Zombies.SCALE);
    }
}
