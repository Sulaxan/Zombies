package me.sulaxan.zombies.util.gui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * A reusable util to create windows quickly.
 */
@Getter
public class Window {

    private JFrame frame;
    private String title;
    private int width;
    private int height;

    public Window(String title, int width, int height, Canvas canvas) {
        this.frame = new JFrame(title);
        this.title = title;
        this.width = width;
        this.height = height;

        // Sizing
        frame.setPreferredSize(new Dimension(width, height));
        frame.setSize(new Dimension(width, height));
        frame.setResizable(false);

        if(canvas != null) frame.add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
