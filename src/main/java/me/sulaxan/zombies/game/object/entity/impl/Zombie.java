package me.sulaxan.zombies.game.object.entity.impl;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.ObjectBound;
import me.sulaxan.zombies.game.object.entity.Collidable;
import me.sulaxan.zombies.game.object.entity.Entity;
import me.sulaxan.zombies.game.object.entity.EntityType;

import java.awt.*;
import java.util.List;

public class Zombie extends Entity implements Collidable {

    private int velocityX;
    private int velocityY;

    public Zombie() {
        super("Zombie", EntityType.ZOMBIE, 20, 1,
                new ObjectBound(4 * (Zombies.SCALE / 2), 4 * (Zombies.SCALE / 2)));
    }

    @Override
    public void onTick() {
        Zombies game = Zombies.getInstance();

        // Movement
        int transposedX = (velocityX >= 1 ? velocityX * getBounds().getWidth() : velocityX);
        int transposedY = (velocityY >= 1 ? velocityY * getBounds().getHeight() : velocityY);
        List<Entity> xEntities = game.getCurrentWorld().getEntitiesAt(getLocation().getX() + transposedX, getLocation().getY());
        List<Entity> yEntities = game.getCurrentWorld().getEntitiesAt(getLocation().getX(), getLocation().getY() + transposedY);
        xEntities.remove(this);
        yEntities.remove(this);

        for(Entity xE : xEntities) {
            if(xE instanceof Barrier) {
                velocityX = 0;
            }
        }
        for(Entity yE : yEntities) {
            if(yE instanceof Barrier) {
                velocityY = 0;
            }
        }

        getLocation().incX(velocityX);
        getLocation().incY(velocityY);

        List<Entity> collisions = game.getCurrentWorld().checkCollision(this);
        // Path finding
        int pX = game.getPlayer().getLocation().getX();
        int pY = game.getPlayer().getLocation().getY();

        velocityX = 0;
        velocityY = 0;

        if(getLocation().getX() < pX) {
            velocityX = 1;
        } else if(getLocation().getX() > pX) {
            velocityX = -1;
        }

        if(getLocation().getY() < pY) {
            velocityY = 1;
        } else if(getLocation().getY() > pY) {
            velocityY = -1;
        }

        // Collision
        for(Entity collide : collisions) {
            if(collide.getEntityType() == EntityType.PLAYER) {
                collide.setHealth(collide.getHealth() - 5);
            }
        }

        // Health
        if(getHealth() <= 0) {
            game.getCurrentWorld().removeEntity(this);
        }
    }

    @Override
    public void onRender(Graphics g) {
        Color zombieColor = Color.GREEN;
        if(getHealth() <= (getMaxHealth() / 2) && getHealth() > (getMaxHealth() / 4)) {
            zombieColor = Color.ORANGE;
        } else if(getHealth() <= (getMaxHealth() / 4)) {
            zombieColor = Color.RED;
        }
        g.setColor(zombieColor);
        g.fillRect(getLocation().getX(), getLocation().getY(), 4 * (Zombies.SCALE / 2), 4 * (Zombies.SCALE / 2));
    }
}
