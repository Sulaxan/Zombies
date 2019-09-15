package me.sulaxan.zombies.game.object.entity;

import me.sulaxan.zombies.util.location.Location;
import me.sulaxan.zombies.game.object.GameObject;
import me.sulaxan.zombies.game.object.ObjectBound;
import me.sulaxan.zombies.game.weapon.Weapon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Entity extends GameObject {

    private String name;
    private EntityType entityType;
    private double maxHealth;
    private double health;
    private float speed;
    private Location location;
    @Setter(AccessLevel.NONE)
    private List<Weapon> weapons;

    public Entity(String name, EntityType type, double maxHealth, float speed, ObjectBound bounds) {
        super(bounds);
        this.name = name;
        this.entityType = type;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.speed = speed;
        this.location = new Location(0, 0, Location.Direction.NORTH);
        this.weapons = new ArrayList<>();
    }
}
