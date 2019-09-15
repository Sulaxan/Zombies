package me.sulaxan.zombies.game.state;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.state.tutorial.StateHowToPlay;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.Util;
import me.sulaxan.zombies.util.audio.AudioManager;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class StateMainMenu extends GameState {

    private Image image;
    private long lastCurrentSongDisplayMessage;

    private Button startButton;
    private Button howToPlayButton;
    private Button quitButton;
    private Button infoButton;

    public StateMainMenu() {
        super("main_menu");
        image = new ImageIcon(ClassLoader.getSystemResource("main.gif")).getImage();
    }

    @Override
    public void onStart() {
        ComponentManager.getInstance().addComponent(startButton = new Button("Start Game", Color.ORANGE,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2 - 55, 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateGame.class));
            }
        });
        ComponentManager.getInstance().addComponent(howToPlayButton = new Button("How to Play", Color.ORANGE,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2 , 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateHowToPlay.class));
            }
        });
        ComponentManager.getInstance().addComponent(quitButton = new Button("Quit Game", Color.ORANGE,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2 + 55, 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setRunning(false);
                System.exit(0);
                ComponentManager.getInstance().removeComponent(this);
            }
        });
        ComponentManager.getInstance().addComponent(infoButton = new Button("Info", Color.ORANGE,
                Zombies.WIDTH - 100, Zombies.HEIGHT - 70, 95, 40) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateInfo.class));
            }
        });

        // Music
        try {
            AudioManager.getInstance().startPlaying();
        } catch (Exception e) {
            System.err.println("Could not play music!");
        }

        lastCurrentSongDisplayMessage = System.currentTimeMillis();
    }

    @Override
    public void onEnd() {
        ComponentManager.getInstance().removeComponent(startButton);
        ComponentManager.getInstance().removeComponent(howToPlayButton);
        ComponentManager.getInstance().removeComponent(quitButton);
        ComponentManager.getInstance().removeComponent(infoButton);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if(image != null) {
            g.drawImage(image, 0, 0, Zombies.WIDTH, Zombies.HEIGHT, null);
        } else {
            g.setColor(Color.PINK);
            g.fillRect(0, 0, Zombies.WIDTH, Zombies.HEIGHT);
        }

        // Title
        String title = "ZOMBIES";
        g.setColor(Color.WHITE);
        Font font = Zombies.getInstance().getDefaultFont().deriveFont(55f);
        // The util will automatically set the font
        g.drawString(title, Util.centerXPosition(title, g, font, Zombies.WIDTH), 100);

        // Subtitle
        String subTitle = "by Sulaxan Pius";
        font = font.deriveFont(20f);
        g.drawString(subTitle, Util.centerXPosition(subTitle, g, font, Zombies.WIDTH), 125);

        // Subversion
        String subVersion = Zombies.getInstance().getSubVersion();
        if(subVersion != null) {
            subVersion = "You are currently playing " + subVersion.trim();
            g.drawString(subVersion, Util.centerXPosition(subVersion, g, font, Zombies.WIDTH), 150);
        }

        font = font.deriveFont(15f);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(Zombies.getInstance().getVersion(), 5, Zombies.HEIGHT - 25);

        // Current song playing
        if(System.currentTimeMillis() - lastCurrentSongDisplayMessage <= TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS)
                && AudioManager.getInstance().isPlaying()) {
            String song = "Now Playing: " + AudioManager.getInstance().getNameByIndex(AudioManager.getInstance().getCurrentSound()).trim();
            int border = 2;
            int distanceFromTop = 10;
            int textWidth = g.getFontMetrics().stringWidth(song);
            g.setFont(font = font.deriveFont(15f));
            g.setColor(Color.BLACK);
            g.fillRect(Zombies.WIDTH - textWidth - border, distanceFromTop, textWidth + border, font.getSize() + (border * 2));
            g.setColor(Color.WHITE);
            g.drawString(song, Zombies.WIDTH - textWidth, distanceFromTop + font.getSize());
        }

        // Force renders
        ComponentManager.getInstance().render(g);
    }
}
