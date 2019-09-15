package me.sulaxan.zombies.game.listener;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.entity.impl.Bullet;
import me.sulaxan.zombies.game.object.entity.impl.player.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    private long lastSpecial;

    @Override
    public void mouseClicked(MouseEvent e) {
        Player p = Zombies.getInstance().getPlayer();
        int mouseX = e.getX() + Zombies.getInstance().getCamera().getX();
        int mouseY = e.getY() + Zombies.getInstance().getCamera().getY();

        if(e.getButton() == MouseEvent.BUTTON1) {
            Bullet bullet = new Bullet(p.getLocation().getX(), p.getLocation().getY(), mouseX, mouseY);
            Zombies.getInstance().getCurrentWorld().getEntities().add(bullet);
        } else if(e.getButton() == MouseEvent.BUTTON3) {
//            // Special
//            if(System.currentTimeMillis() - lastSpecial >= TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS)) {
//                // to create a circle: sqrt(r^2 - (x - h)^2) + k
//
//                Location loc = p.getLocation().clone();
//                int radius = 6;
//                for(int x = loc.getX() - radius; x < loc.getX() + radius; x++) {
//                    double y = (Math.sqrt(Math.pow(radius, 2) - Math.pow(x - loc.getX(), 2))) + loc.getY();
//                    Bullet bullet = new Bullet(loc.getX() + 14, loc.getY() + 14, x, (int) y);
//                    Zombies.getInstance().getCurrentWorld().getEntities().add(bullet);
//                }
//                for(int x = loc.getX() - radius; x < loc.getX() + radius; x++) {
//                    double y = -(Math.sqrt(Math.pow(radius, 2) - Math.pow(x - loc.getX(), 2))) + loc.getY();
//                    Bullet bullet = new Bullet(loc.getX() + 14, loc.getY() + 14, x, (int) y);
//                    Zombies.getInstance().getCurrentWorld().getEntities().add(bullet);
//                }
//            }
            this.lastSpecial = System.currentTimeMillis();
        }
    }
}
