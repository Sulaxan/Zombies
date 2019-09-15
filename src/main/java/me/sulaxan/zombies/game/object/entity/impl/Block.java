package me.sulaxan.zombies.game.object.entity.impl;

import lombok.Getter;
import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.ObjectBound;
import me.sulaxan.zombies.game.object.entity.Entity;
import me.sulaxan.zombies.game.object.entity.EntityType;

import java.awt.*;

@Getter
public class Block extends Entity {

    private Color color;

    public Block(Color color) {
        super("Block", EntityType.UNKNOWN, -1, -1, new ObjectBound(1, 1));
        this.color = color;
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics g) {
        g.setColor(color);
        g.fillRect(getLocation().getX(), getLocation().getY(), Zombies.SCALE, Zombies.SCALE);
    }
}
