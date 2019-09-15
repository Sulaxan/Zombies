package me.sulaxan.zombies.util;

import me.sulaxan.zombies.game.Zombies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {

    // Prevents initialization
    private ImageUtil() {}

    /**
     * Loads an image within the current jar via class loader/resource
     * streaming.
     *
     * @param path The path to the image to load.
     * @return The buffered image.
     */
    public static BufferedImage loadImageFromJar(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Zombies.getInstance().getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
