package me.sulaxan.zombies.game.state;

import kotlin.KotlinVersion;
import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.Util;
import me.sulaxan.zombies.util.audio.AudioManager;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;
import me.sulaxan.zombies.util.text.MultiLineBuilder;
import me.sulaxan.zombies.util.text.TextInfo;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class StateInfo extends GameState {

    public StateInfo() {
        super("info");
    }

    @Override
    public void onStart() {
        ComponentManager.getInstance().addComponent(new Button("Go Back", Color.YELLOW,
                Zombies.WIDTH / 2 - 50, Zombies.HEIGHT - 100, 100, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateMainMenu.class));
                ComponentManager.getInstance().removeComponent(this);
            }
        });
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, Zombies.WIDTH, Zombies.HEIGHT);

        g.setColor(Color.BLACK);

        Font font = Zombies.getInstance().getDefaultFont().deriveFont(20f);

        MultiLineBuilder builder = new MultiLineBuilder();
        builder.setDefaultFont(font);
        builder.setDefaultColor(Color.BLACK);
        builder.addLine(new TextInfo("Information", font.deriveFont(27f), Color.WHITE));
        builder.addLine("Below you can find useful information.");
        builder.addLine(" ");
        builder.addLine("Zombies Version: " + Zombies.getInstance().getVersion());
        builder.addLine("Java Version: " + System.getProperty("java.version") + " (1.7.x+ support)");
        builder.addLine("Kotlin Version: " + KotlinVersion.CURRENT);
        builder.addLine("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"));
        builder.addLine("Available Sound Tracks: " + AudioManager.getInstance().getSounds().size());
        builder.addLine(" ");
        builder.addLine("Current FPS: " + Zombies.getInstance().getFrames());
        builder.addLine(" ");
        builder.addLine("Play Time (Session): " + Util.formatTimeToMinSec(Zombies.getInstance().getPlayTime(), TimeUnit.MILLISECONDS));
        builder.draw(g, Zombies.WIDTH / 2, 50);

        ComponentManager.getInstance().render(g);
    }
}
