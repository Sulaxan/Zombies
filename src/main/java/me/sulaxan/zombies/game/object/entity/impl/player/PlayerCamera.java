package me.sulaxan.zombies.game.object.entity.impl.player;

import me.sulaxan.zombies.game.Zombies;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerCamera {

    private int x, y;

    /**
     * Updates the positioning based on the player's location.
     * @param player The player to position around.
     */
    public void update(Player player) {
        x = player.getLocation().getX() - (Zombies.WIDTH / 2);
        y = player.getLocation().getY() - (Zombies.HEIGHT / 2);

        if(x <= 0) x = 0;
        // Have to add the width as the camera x value is in the top left corner.
        if(x + Zombies.WIDTH >= Zombies.getInstance().getCurrentWorld().getImage().getWidth() * Zombies.SCALE)
            x = (Zombies.getInstance().getCurrentWorld().getImage().getWidth() * Zombies.SCALE) - Zombies.WIDTH;

        if(y <= 0) y = 0;
        if(y + Zombies.HEIGHT >= Zombies.getInstance().getCurrentWorld().getImage().getHeight() * Zombies.SCALE)
            y = (Zombies.getInstance().getCurrentWorld().getImage().getHeight() * Zombies.SCALE) - Zombies.HEIGHT;
    }
}
