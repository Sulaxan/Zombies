package me.sulaxan.zombies.game.world;

import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.entity.impl.Block;
import me.sulaxan.zombies.game.object.entity.impl.Zombie;
import me.sulaxan.zombies.util.location.Location;
import me.sulaxan.zombies.game.object.entity.Collidable;
import me.sulaxan.zombies.game.object.entity.Entity;
import me.sulaxan.zombies.game.object.entity.impl.Barrier;
import me.sulaxan.zombies.util.ConcurrentList;
import me.sulaxan.zombies.util.ImageUtil;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
public class World {

    private String name;
    private String fileLocation;
    private ConcurrentList<Entity> entities;
    private BufferedImage image;

    private List<Location> spawnLocations;

    public World(String name, String fileLocation) {
        this.name = name;
        this.fileLocation = fileLocation;
        this.entities = new ConcurrentList<>();
        this.spawnLocations = new ArrayList<>();

        Zombie zombie = new Zombie();
        zombie.setLocation(new Location(150, 150, Location.Direction.NORTH));
        getEntities().add(zombie);

        loadWorld();
    }

    /**
     * Loads the world using the provided file location.
     */
    private void loadWorld() {
        this.image = ImageUtil.loadImageFromJar(fileLocation);
        int height = image.getHeight(null);
        int width = image.getWidth(null);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                // Setting barriers as appropriate
                // Inside walls
                if(red == 185 && green == 122 && blue == 87) {
                    Barrier barrier = new Barrier(Color.ORANGE);
                    barrier.setLocation(new Location(x * Zombies.SCALE, y * Zombies.SCALE, Location.Direction.NORTH));
                    getEntities().add(barrier);
                }
                // Outside walls
                if(red == 127 && green == 127 && blue == 127) {
                    Barrier barrier = new Barrier(Color.RED);
                    barrier.setLocation(new Location(x * Zombies.SCALE, y * Zombies.SCALE, Location.Direction.NORTH));
                    getEntities().add(barrier);
                }

                // Zombie spawn locations
                if(red == 255 && green == 127 && blue == 39) {
                    Location loc = new Location(x * Zombies.SCALE, y * Zombies.SCALE, Location.Direction.NORTH);
                    spawnLocations.add(loc);

                    Block block = new Block(new Color(192, 155, 114));
                    block.setLocation(loc);
                    getEntities().add(block);
                }
            }
        }
    }

    /**
     * Adds a new entity to the world.
     * @param entity The entity to add.
     */
    public void spawnEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the world.
     * @param entity The entity to remove.
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Finds all entities at a given location.
     * @param x The x coordinate to search.
     * @param y The y coordinate to search.
     * @return The list of all entities at the given point.
     */
    public List<Entity> getEntitiesAt(int x, int y) {
        List<Entity> entities = new ArrayList<>();
        for(Entity entity : getEntities().getConcurrentSafe()) {
            if(x >= entity.getLocation().getX() && x <= (entity.getLocation().getX() + entity.getBounds().getWidth())) {
                if(y >= entity.getLocation().getY() && y <= (entity.getLocation().getY() + entity.getBounds().getHeight())) {
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    /**
     * Checks if an entity is in another entity's collision
     * area.
     * @param entity The entity to check collision for.
     * @return A list of entities that are colliding with the
     * specified entity.
     */
    public List<Entity> checkCollision(Entity entity) {
        List<Entity> entities = new ArrayList<>();
        if(entity instanceof Collidable) {
            Rectangle entityBounds = new Rectangle(
                    entity.getLocation().getX(),
                    entity.getLocation().getY(),
                    entity.getBounds().getWidth(),
                    entity.getBounds().getHeight()
            );
            for(Entity entity2 : this.entities.getConcurrentSafe()) {
                if(entity != entity2) {
                    if(entity2 instanceof Collidable) {
                        Rectangle collideEntityBounds = new Rectangle(
                                entity2.getLocation().getX(),
                                entity2.getLocation().getY(),
                                entity2.getBounds().getWidth(),
                                entity2.getBounds().getHeight()
                        );
                        if(entityBounds.intersects(collideEntityBounds)) {
                            entities.add(entity2);
                        }
                    }
                }
            }
        }
        return entities;
    }

    /**
     * Updates all entities currently in the world.
     */
    public void tick() {
        for(Entity entity : entities.getConcurrentSafe()) {
            entity.onTick();
        }
    }

    /**
     * Renders the world and all entities in the world.
     * @param g The graphics to use to render.
     */
    public void render(Graphics g) {
        g.drawImage(image,
                0,
                0,
                image.getWidth() * Zombies.SCALE,
                image.getHeight() * Zombies.SCALE,
                null);
        for(Entity entity : entities.getConcurrentSafe()) {
            entity.onRender(g);
        }
        // Drawing the minimap
//        final int transposeX = 5;
//        final int transposeY = 14;
//
//        g.setColor(Color.BLACK);
//        g.setFont(new Font("Arial", Font.BOLD, 15));
//        g.drawString("MINIMAP", 0, 12);
//        for(int x = 0; x < image.getWidth(); x++) {
//            for(int y = 0; y < image.getHeight(); y++) {
//                int pixel = image.getRGB(x, y);
//                int red = (pixel >> 16) & 0xff;
//                int green = (pixel >> 8) & 0xff;
//                int blue = pixel & 0xff;
//
//                g.setColor(Color.BLACK);
//                if(red == 185 && green == 122 && blue == 87) {
//                    g.drawRect((x / 2) + transposeX, (y / 2) + transposeY, 1, 1);
//                }
//            }
//        }
//        Location playerLoc = Zombies.getInstance().getPlayer().getLocation();
//        g.setColor(Color.RED);
//        g.drawRect((playerLoc.getX() / Zombies.SCALE) + transposeX, (playerLoc.getY() / Zombies.SCALE) + transposeY, 1, 1);
    }
}
