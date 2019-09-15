package me.sulaxan.zombies.game.state.tutorial;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;
import me.sulaxan.zombies.util.text.MultiLineBuilder;
import me.sulaxan.zombies.util.text.TextInfo;

import java.awt.*;

public class StateControls extends GameState {

    public StateControls() {
        super("controls");
    }

    @Override
    public void onStart() {
        ComponentManager.getInstance().addComponent(new Button("Go Back", Color.YELLOW,
                Zombies.WIDTH / 2 - 50, Zombies.HEIGHT - 100, 100, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateHowToPlay.class));
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

        Font font = Zombies.getInstance().getDefaultFont();

        MultiLineBuilder builder = new MultiLineBuilder();
        builder.setDefaultFont(font.deriveFont(20f));
        builder.setDefaultColor(Color.BLACK);
        builder.addLine(new TextInfo("Controls", font.deriveFont(27f), Color.WHITE));
        builder.addLine(" ");
        builder.addLine("----- Movement -----");
        builder.addLine("[ W ] - Move Up");
        builder.addLine("[ S ] - Move Down");
        builder.addLine("[ A ] - Move Left");
        builder.addLine("[ D ] - Move Right");
        builder.addLine("[ P ] - Pause");
        builder.addLine(" ");
        builder.addLine("----- Shooting -----");
        builder.addLine("Left Mouse Click - Shoot");
        builder.addLine("Right Mouse Click - Shoot (Special)");
        builder.addLine(new TextInfo("(COMING SOON)", builder.getDefaultFont(), Color.RED));
        builder.draw(g, Zombies.WIDTH / 2, 50);

        ComponentManager.getInstance().render(g);
    }
}
