package me.sulaxan.zombies.game.weapon;

import me.sulaxan.zombies.game.object.entity.Entity;
import lombok.Getter;

@Getter
public abstract class Gun extends Weapon {

    private double bulletRange;

    public Gun(String name, double damage, double bulletRange) {
        super(name, damage);
        this.bulletRange = bulletRange;
    }

    public abstract void onEntityHit(Entity entity);
}
