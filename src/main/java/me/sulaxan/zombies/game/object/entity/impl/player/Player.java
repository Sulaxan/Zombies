package me.sulaxan.zombies.game.object.entity.impl.player;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.ObjectBound;
import me.sulaxan.zombies.game.object.entity.Collidable;
import me.sulaxan.zombies.game.object.entity.Entity;
import me.sulaxan.zombies.game.object.entity.EntityType;
import lombok.Getter;
import me.sulaxan.zombies.game.object.entity.impl.Barrier;
import me.sulaxan.zombies.util.location.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

@Getter
public class Player extends Entity implements Collidable {

    private float velocityX;
    private float velocityY;

    public static final double DEFAULT_HEALTH = 1000;

    public Player() {
        super("Player", EntityType.PLAYER, DEFAULT_HEALTH, 1,
                new ObjectBound(4 * (Zombies.SCALE / 2), 4 * (Zombies.SCALE / 2)));
        setLocation(new Location(1695, 525, Location.Direction.NORTH));
    }

    /**
     * Updates the player's positioning.
     */
    @Override
    public void onTick() {
        getLocation().incX((int) velocityX);
        getLocation().incY((int) velocityY);

        List<Entity> collisions = Zombies.getInstance().getCurrentWorld().checkCollision(this);

        // Collision
        if(collisions.size() >= 1) {
            boolean containsBarrier = false;
            for(Entity collision : collisions) {
                if(collision instanceof Barrier) containsBarrier = true;
            }
            if(containsBarrier) {
                getLocation().incX((int) velocityX * -1);
                getLocation().incY((int) velocityY * -1);
            }
        }

        // Movement
        boolean[] pressedKeys = ControlManager.getInstance().getPressedKeys();

        // Up and down
        if(pressedKeys[KeyEvent.VK_W]) velocityY = -5;
        if(pressedKeys[KeyEvent.VK_S]) velocityY = 5;

        if(!pressedKeys[KeyEvent.VK_W] && !pressedKeys[KeyEvent.VK_S]) velocityY = 0;

        // Left and right
        if(pressedKeys[KeyEvent.VK_A]) velocityX = -5;
        if(pressedKeys[KeyEvent.VK_D]) velocityX = 5;

        if(!pressedKeys[KeyEvent.VK_A] && !pressedKeys[KeyEvent.VK_D]) velocityX = 0;

        // Setting the game to the end state when the player has 0 health
        if(getHealth() <= 0.0d) {
            Zombies.getInstance().nextState();
        }
    }

    /**
     * Renders the player.
     * @param g The graphics to use to render the player.
     */
    @Override
    public void onRender(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(getLocation().getX(), getLocation().getY(), 4 * (Zombies.SCALE / 2), 4 * (Zombies.SCALE / 2));
    }
}
