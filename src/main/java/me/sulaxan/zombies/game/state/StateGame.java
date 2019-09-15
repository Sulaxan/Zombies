package me.sulaxan.zombies.game.state;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.listener.MouseListener;
import me.sulaxan.zombies.game.object.entity.impl.player.Player;
import me.sulaxan.zombies.game.state.result.StateGameOver;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.audio.AudioManager;
import me.sulaxan.zombies.game.world.RoundManager;

import java.awt.*;

public class StateGame extends GameState {

    private MouseListener mouseListener;

    public StateGame() {
        super("game");
    }

    @Override
    public void onStart() {
        Zombies.getInstance().addMouseListener(this.mouseListener = new MouseListener());
        AudioManager.getInstance().stopPlaying();
    }

    @Override
    public void onEnd() {
        Zombies.getInstance().removeMouseListener(this.mouseListener);
    }

    @Override
    public void tick() {
        RoundManager.getInstance().tick();
        Zombies.getInstance().getCamera().update(Zombies.getInstance().getPlayer());
        Zombies.getInstance().getCurrentWorld().tick();

        if(Zombies.getInstance().getPlayer().getHealth() <= 0) {
            Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateGameOver.class));
        }
    }

    @Override
    public void render(Graphics g) {
        Zombies game = Zombies.getInstance();
        Player p = game.getPlayer();
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(-game.getCamera().getX(), -game.getCamera().getY());

        RoundManager.getInstance().render(g);
        game.getCurrentWorld().render(g);

        // Info bar at the top

        // Health
        int distanceFromTop = 30;
        int distanceFromSide = 10;
        Font font = Zombies.getInstance().getDefaultFont().deriveFont(15f);

        g.setFont(font);
        g.setColor(Color.RED);
        String healthText = "Health (" + p.getHealth() + "/" + p.getMaxHealth() + ")";
        g.drawString(healthText, game.getCamera().getX() + distanceFromSide, game.getCamera().getY() + distanceFromTop);

        g.setColor(Color.GREEN);
        int healthBar = (int) ((p.getHealth() / p.getMaxHealth()) * 100);
        g.fillRect(game.getCamera().getX() + (distanceFromSide * 2) + g.getFontMetrics().stringWidth(healthText),
                game.getCamera().getY() + (distanceFromTop / 2),
                healthBar,
                font.getSize());

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString("Round: " + RoundManager.getInstance().getRound().getWave()
                + " - Zombies Left: " + RoundManager.getInstance().getRoundZombies().size(),
                game.getCamera().getX() + distanceFromSide + g.getFontMetrics().stringWidth(healthText) + 20 + healthBar,
                game.getCamera().getY() + distanceFromTop);
    }
}
