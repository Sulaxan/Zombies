package me.sulaxan.zombies.game.state.result;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.Util;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.util.component.impl.Button;

import java.awt.*;

public class StateWin extends GameState {

    private Button mainMenuButton;

    public StateWin() {
        super("win");
    }

    @Override
    public void onStart() {
        ComponentManager.getInstance().addComponent(this.mainMenuButton = new Button("Main Menu", Color.CYAN,
                Zombies.WIDTH / 2 - 125, Zombies.HEIGHT / 2, 250, 50) {
            @Override
            public void onButtonClick() {
                Zombies.getInstance().setState(0);
                Zombies.getInstance().reset();
            }
        });
    }

    @Override
    public void onEnd() {
        ComponentManager.getInstance().removeComponent(mainMenuButton);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
        g2d.setPaint(Color.GREEN);
        g2d.setComposite(composite);
        g2d.fill(new Rectangle(0, 0, Zombies.WIDTH, Zombies.HEIGHT));

        g.setColor(Color.BLACK);

        Font font = Zombies.getInstance().getDefaultFont().deriveFont(50f);
        String gameOver = "You Won!";
        g.drawString(gameOver, Util.centerXPosition(gameOver, g, font, Zombies.WIDTH), 50);
    }
}