package me.sulaxan.zombies.game.object;

import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ObjectManager {

    private static ObjectManager instance;
    private List<GameObject> objects;

    private ObjectManager() {
        this.objects = new ArrayList<>();
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }

    public void tick() {
        for(GameObject object : objects) {
            object.onTick();
        }
    }

    public void render(Graphics g) {
        for(GameObject object : objects) {
            object.onRender(g);
        }
    }

    public static ObjectManager getInstance() {
        if(instance == null) instance = new ObjectManager();
        return instance;
    }
}
