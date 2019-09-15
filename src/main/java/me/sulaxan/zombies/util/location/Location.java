package me.sulaxan.zombies.util.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location implements Cloneable {

    private int x;
    private int y;
    private Direction direction;

    public void incX(int x) {
        this.x += x;
    }

    public void incY(int y) {
        this.y += y;
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}

