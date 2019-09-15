package me.sulaxan.zombies.game.state;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.Util;
import me.sulaxan.zombies.util.audio.AudioManager;
import me.sulaxan.zombies.util.text.MultiLineBuilder;
import me.sulaxan.zombies.util.text.TextInfo;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class StatePause extends GameState {

    private Image image;
    private long pauseTime;

    public StatePause() {
        super("pause");
        image = new ImageIcon(ClassLoader.getSystemResource("main.gif")).getImage();
    }

    @Override
    public void onStart() {
        this.pauseTime = System.currentTimeMillis();
        try {
            AudioManager.getInstance().startPlaying();
        } catch (Exception e) {
            System.err.println("Could not play music (game_state=pause)!");
        }
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, 0, 0, Zombies.WIDTH, Zombies.HEIGHT, null);

        Font font = Zombies.getInstance().getDefaultFont();
        MultiLineBuilder builder = new MultiLineBuilder();
        builder.setDefaultFont(font.deriveFont(30f));
        builder.setDefaultColor(Color.WHITE);
        builder.addLine(new TextInfo("PAUSED", font.deriveFont(Font.BOLD,50f), Color.WHITE));
        builder.addLine(" ");
        builder.addLine("Press [ P ] again to unpause!");
        String time = Util.formatTimeToMinSec(System.currentTimeMillis() - pauseTime, TimeUnit.MILLISECONDS);
        builder.addLine(" ");
        builder.addLine(new TextInfo("You've been paused for " + time, font.deriveFont(20f), Color.WHITE));
        builder.draw(g, Zombies.WIDTH / 2, Zombies.HEIGHT / 2 - 125);
    }
}
