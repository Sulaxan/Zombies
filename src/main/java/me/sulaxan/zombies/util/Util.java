package me.sulaxan.zombies.util;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Util {

    // Prevent initialization
    private Util() {
    }

    public static int centerXPosition(String text, Graphics g, Font font, int width) {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        return ((width / 2) - (textWidth / 2));
    }

    public static int centerXPositionUsingPoint(String text, Graphics g, Font font, int x) {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        return (x - (textWidth / 2));
    }

    public static String formatTime(long time, TimeUnit unit) {
        return (time == 1 ? time + " " + unit.name().toLowerCase().substring(0, unit.name().length() - 1)
                : time + " " + unit.name().toLowerCase());
    }

    public static String formatTimeToMinSec(long time, TimeUnit unit) {
        long seconds = TimeUnit.SECONDS.convert(time, unit);
        return formatTime(seconds / 60, TimeUnit.MINUTES) + " " + formatTime(seconds % 60, TimeUnit.SECONDS);
    }
}
