package me.sulaxan.zombies.game.state.result;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.state.StateMainMenu;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;
import me.sulaxan.zombies.util.text.MultiLineBuilder;
import me.sulaxan.zombies.util.text.TextInfo;
import me.sulaxan.zombies.game.world.RoundManager;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class StateGameOver extends GameState {

    private long countdown;
    private final long totalCountdownSeconds = 10;

    private Button mainMenuButton;

    public StateGameOver() {
        super("end");
    }

    @Override
    public void onStart() {
        this.countdown = System.currentTimeMillis();
        ComponentManager.getInstance().addComponent(this.mainMenuButton = new Button("Main Menu", Color.CYAN,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2 + 100, 250, 50) {
            @Override
            public void onButtonClick() {
                ComponentManager.getInstance().removeComponent(this);
                Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateMainMenu.class));
                Zombies.getInstance().reset();
            }
        });
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() - countdown >= TimeUnit.MILLISECONDS.convert(totalCountdownSeconds, TimeUnit.SECONDS)) {
            ComponentManager.getInstance().removeComponent(mainMenuButton);
            Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateMainMenu.class));
            Zombies.getInstance().reset();
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
//        Composite defaultComposite = g2d.getComposite();
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
        g2d.setPaint(Color.RED);
        g2d.setComposite(composite);
        g2d.fill(new Rectangle(0, 0, Zombies.WIDTH, Zombies.HEIGHT));

        g.setColor(Color.BLACK);
//        g2d.setComposite(defaultComponsite);

        Font font = Zombies.getInstance().getDefaultFont();
        MultiLineBuilder builder = new MultiLineBuilder();
        builder.setDefaultFont(font.deriveFont(30f));
        builder.setDefaultColor(Color.BLACK);
        builder.addLine(new TextInfo("GAME OVER", font.deriveFont(50f), Color.BLACK));
        builder.addLine("You survived until round " + RoundManager.getInstance().getRound().getWave() + "!");
        builder.addLine(new TextInfo(" ", font.deriveFont(50f), Color.BLACK));
        String countdown = new DecimalFormat("#.#").format((double) totalCountdownSeconds - ((double) System.currentTimeMillis() - this.countdown) / (double) 1000) + "s until main menu!";
        builder.addLine(new TextInfo(countdown, font.deriveFont(50f), Color.BLACK));
        builder.draw(g, Zombies.WIDTH / 2, 100);
    }
}
