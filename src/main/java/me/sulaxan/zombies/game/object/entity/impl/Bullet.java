package me.sulaxan.zombies.game.object.entity.impl;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.location.Location;
import me.sulaxan.zombies.game.object.ObjectBound;
import me.sulaxan.zombies.game.object.entity.Collidable;
import me.sulaxan.zombies.game.object.entity.Entity;
import me.sulaxan.zombies.game.object.entity.EntityType;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Bullet extends Entity implements Collidable {

    private int velocityX;
    private int velocityY;
    private double damage;
    private long spawnTime;

    public Bullet(int x, int y, int dx, int dy) {
        super("Bullet", EntityType.BULLET, -1, -1, new ObjectBound(2, 2));
        setLocation(new Location(x, y, Location.Direction.NORTH));
        this.velocityX = (dx - x) / 10;
        this.velocityY = (dy - y) / 10;
        this.damage = 5.0d;
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    public void onTick() {
        getLocation().incX(velocityX);
        getLocation().incY(velocityY);

        boolean removeEntity = false;

        for(Entity collide : Zombies.getInstance().getCurrentWorld().checkCollision(this)) {
            if (collide.getEntityType() != EntityType.PLAYER && collide.getEntityType() != EntityType.BULLET) {
                if (collide instanceof Zombie) {
                    collide.setHealth(collide.getHealth() - damage);
                }
                removeEntity = true;
                break;
            }
        }

        // Despawns after 5 seconds
        if(System.currentTimeMillis() - spawnTime >= TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS)) {
            removeEntity = true;
        }

        if(removeEntity) {
            Zombies.getInstance().getCurrentWorld().removeEntity(this);
        }
    }

    @Override
    public void onRender(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawOval(getLocation().getX(), getLocation().getY(), 2, 2);
    }
}
