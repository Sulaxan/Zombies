package me.sulaxan.zombies.game.world;

import lombok.Getter;
import me.sulaxan.zombies.game.Zombies;
import me.sulaxan.zombies.game.object.entity.impl.Zombie;
import me.sulaxan.zombies.util.ConcurrentList;
import me.sulaxan.zombies.util.location.Location;

import java.awt.*;
import java.util.Random;

public class RoundManager {

    private static RoundManager instance;
    private Random random;
    @Getter private Round round;
    private long lastSpawn;

    @Getter private ConcurrentList<Zombie> roundZombies;
    private int currentSpawns;

    private RoundManager() {
        this.random = new Random();
        this.lastSpawn = 0;
        this.roundZombies = new ConcurrentList<>();
        resetRounds();
    }

    public void nextRound() {
        this.round.nextWave();
        this.currentSpawns = 0;
        for(Zombie zombie : roundZombies) {
            Zombies.getInstance().getCurrentWorld().removeEntity(zombie);
        }
        roundZombies.clear();
    }

    public void resetRounds() {
        this.round = new Round(1, 5, 2);
        this.currentSpawns = 0;
        for(Zombie zombie : roundZombies) {
            Zombies.getInstance().getCurrentWorld().removeEntity(zombie);
        }
        roundZombies.clear();
    }

    public void tick() {
        if(roundZombies.size() <= 0 && currentSpawns >= (round.getWaveZombies() - 1)) {
            nextRound();
        }
        Zombies game = Zombies.getInstance();
        for(Zombie zombie : roundZombies.getConcurrentSafe()) {
            if(!game.getCurrentWorld().getEntities().contains(zombie)) {
                roundZombies.remove(zombie);
            }
        }
        if((currentSpawns < round.getWaveZombies()) && ((System.currentTimeMillis() - lastSpawn) >= 2000)) {
            Zombie zombie = new Zombie();
            Location loc = game.getCurrentWorld().getSpawnLocations().get(random.nextInt(game.getCurrentWorld().getSpawnLocations().size()));
            if(loc != null) zombie.setLocation(loc.clone());
            this.roundZombies.add(zombie);
            game.getCurrentWorld().spawnEntity(zombie);
            this.lastSpawn = System.currentTimeMillis();
            this.currentSpawns++;
        }
    }

    public void render(Graphics g) {

    }

    public static RoundManager getInstance() {
        if(instance == null) instance = new RoundManager();
        return instance;
    }
}
