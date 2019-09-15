package me.sulaxan.zombies.util.component.impl;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.util.Util;
import me.sulaxan.zombies.util.component.GenericComponent;
import lombok.Getter;

import java.awt.*;

@Getter
public abstract class Button extends GenericComponent {

    private String text;
    private Color color;
    private int x;
    private int y;
    private int dx;
    private int dy;

    public Button(String text, Color color, int x, int y, int dx, int dy) {
        this.text = text;
        this.color = color;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public final void tick() {

    }

    @Override
    public final void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
//        Composite defaultComposite = g2d.getComposite();
        g2d.setPaint(color);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.fillRect(x, y, dx, dy);

        Font font = Zombies.getInstance().getDefaultFont().deriveFont(27f);
        g2d.setFont(font);
        g2d.setPaint(Color.BLACK);
        g2d.drawString(text, Util.centerXPositionUsingPoint(text, g, font, x + (dx / 2)), y + Math.abs(dy - (font.getSize() / 2)));
    }

    public abstract void onButtonClick();
}
