package me.sulaxan.zombies.game.world;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Round {

    private int wave;
    private int waveZombies;
    private int zombieIncrement;

    public Round(int wave, int waveZombies, int zombieIncrement) {
        this.wave = wave;
        this.waveZombies = waveZombies;
        this.zombieIncrement = zombieIncrement;
    }

    public void nextWave() {
        this.wave++;
        this.waveZombies += this.zombieIncrement;
    }
}
