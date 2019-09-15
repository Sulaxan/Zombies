package me.sulaxan.zombies.util.component;

import lombok.Getter;
import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.ConcurrentList;
import me.sulaxan.zombies.util.component.impl.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class ComponentManager implements MouseListener {

    private static ComponentManager instance;
    @Getter private ConcurrentList<GenericComponent> components;

    private ComponentManager() {
        this.components = new ConcurrentList<>();
        Zombies.getInstance().addMouseListener(this);
    }

    public GenericComponent getComponent(int id) {
        for(GenericComponent component : components) {
            if(component.getId() == id) {
                return component;
            }
        }
        return null;
    }

    public void addComponent(GenericComponent component) {
        if(component != null) {
            component.setId(genId());
            this.components.add(component);
        }
    }

    public void removeComponent(GenericComponent component) {
        if(component != null) {
            components.remove(component);
        }
    }

    private int genId() {
        Random r = new Random();
        int id;

        do {
            id = r.nextInt(1000) + 1;
        } while(getComponent(id) != null);

        return id;
    }

    public void tick() {
        for(GenericComponent component : components.getConcurrentSafe()) {
            component.tick();
        }
    }

    public void render(Graphics g) {
        for(GenericComponent component : components.getConcurrentSafe()) {
            component.render(g);
        }
    }

    public static ComponentManager getInstance() {
        if(instance == null) instance = new ComponentManager();
        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(GenericComponent component : components.getConcurrentSafe()) {
            if(component instanceof Button) {
                Button button = (Button) component;
                if(e.getX() >= button.getX() && e.getX() <= button.getX() + button.getDx()) {
                    if(e.getY() >= button.getY() && e.getY() <= button.getY() + button.getDy()) {
                        button.onButtonClick();
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for(GenericComponent component : components.getConcurrentSafe()) {
            if(component instanceof Button) {
                Button button = (Button) component;
                if(e.getX() >= button.getX() && e.getX() <= button.getX() + button.getDx()) {
                    if(e.getY() >= button.getY() && e.getY() <= button.getY() + button.getDy()) {
                        button.onButtonClick();
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
