package me.sulaxan.zombies.game.object.entity.impl.player;

import me.sulaxan.zombies.game.Zombies;
import lombok.Getter;

import java.awt.event.*;

public class ControlManager implements KeyListener {

    private static ControlManager instance;
    @Getter private boolean[] pressedKeys = new boolean[1500];

    private ControlManager() {
        Zombies.getInstance().addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_P) {
            Zombies.getInstance().setPaused(!Zombies.getInstance().isPaused());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
    }

    public static ControlManager getInstance() {
        if(instance == null) instance = new ControlManager();
        return instance;
    }
}
