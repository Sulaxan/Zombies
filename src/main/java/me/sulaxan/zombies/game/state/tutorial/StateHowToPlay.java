package me.sulaxan.zombies.game.state.tutorial;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.state.StateMainMenu;
import me.sulaxan.zombies.game.state.StateModeInfoMenu;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;
import me.sulaxan.zombies.util.text.MultiLineBuilder;
import me.sulaxan.zombies.util.text.TextInfo;

import java.awt.*;

public class StateHowToPlay extends GameState {

    private Button controlsButton;
    private Button modeButton;
    private Button backButton;

    public StateHowToPlay() {
        super("how_to_play");
    }

    @Override
    public void onStart() {
        ComponentManager.getInstance().addComponent(controlsButton = new Button("Controls", Color.YELLOW,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2 - 55, 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateControls.class));
            }
        });
        ComponentManager.getInstance().addComponent(modeButton = new Button("Mode Info", Color.YELLOW,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2, 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateModeInfoMenu.class));
            }
        });
        ComponentManager.getInstance().addComponent(backButton = new Button("Go Back", Color.YELLOW,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2 + 55, 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateMainMenu.class));
            }
        });
    }

    @Override
    public void onEnd() {
        ComponentManager.getInstance().removeComponent(controlsButton);
        ComponentManager.getInstance().removeComponent(modeButton);
        ComponentManager.getInstance().removeComponent(backButton);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, Zombies.WIDTH, Zombies.HEIGHT);

        g.setColor(Color.WHITE);
        Font font = Zombies.getInstance().getDefaultFont();
        MultiLineBuilder builder = new MultiLineBuilder();
        builder.setDefaultFont(font.deriveFont(20f));
        builder.setDefaultColor(Color.BLACK);
        builder.addLine(new TextInfo("How to Play", font.deriveFont(27f), Color.WHITE));
        builder.addLine("Use the buttons below to navigate the");
        builder.addLine("How to Play menu!");
        builder.draw(g, Zombies.WIDTH / 2, 50);

        ComponentManager.getInstance().render(g);
    }
}
