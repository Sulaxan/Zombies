package me.sulaxan.zombies.util.text;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.sulaxan.zombies.util.ConcurrentList;
import me.sulaxan.zombies.util.Util;

import java.awt.*;

@Getter
@Setter
public class MultiLineBuilder {

    @Setter(AccessLevel.NONE) private ConcurrentList<TextInfo> lines;
    private Font defaultFont;
    private Color defaultColor;
    private boolean center;

    public MultiLineBuilder() {
        this.lines = new ConcurrentList<>();
        this.defaultFont = new Font("Arial", Font.PLAIN, 10);
        this.defaultColor = Color.BLACK;
        this.center = true;
    }

    public void addLine(TextInfo info) {
        lines.add(info);
    }

    public void addLine(String text) {
        TextInfo info = new TextInfo(text, defaultFont, defaultColor);
        addLine(info);
    }

    public void draw(Graphics g, int startX, int startY) {
        int nextY = startY;
        for(TextInfo info : lines) {
            int transposedX = (center ? Util.centerXPositionUsingPoint(info.getText(), g, info.getFont(), startX) : startX);
            g.setFont(info.getFont());
            g.setColor(info.getColor());
            g.drawString(info.getText(), transposedX, nextY);
            nextY += info.getFont().getSize();
        }
    }
}
