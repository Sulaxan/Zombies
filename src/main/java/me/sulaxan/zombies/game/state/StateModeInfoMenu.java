package me.sulaxan.zombies.game.state;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.state.tutorial.StateHowToPlay;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;
import me.sulaxan.zombies.util.text.MultiLineBuilder;
import me.sulaxan.zombies.util.text.TextInfo;

import java.awt.*;

public class StateModeInfoMenu extends GameState {

    private Button backButton;

    public StateModeInfoMenu() {
        super("mode_info_menu");
    }

    @Override
    public void onStart() {
        ComponentManager.getInstance().addComponent(this.backButton = new Button("Go Back",
                Color.YELLOW,
                Zombies.WIDTH / 2 - 50, Zombies.HEIGHT - 125, 100, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateHowToPlay.class));
                ComponentManager.getInstance().removeComponent(backButton);
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
        builder.addLine(new TextInfo("Zombies Mode Information", font.deriveFont(27f), Color.WHITE));
        builder.addLine(" ");
        builder.addLine("Infinite - Unlimited rounds - (almost) endless fun!");
        builder.addLine(" ");
        builder.addLine("Round Cap - Rounds are capped at x, you win after");
        builder.addLine("completing the last round!");
        builder.addLine(new TextInfo("(COMING SOON)", builder.getDefaultFont(), Color.RED));
        builder.draw(g, Zombies.WIDTH / 2, 50);

        ComponentManager.getInstance().render(g);
    }
}
