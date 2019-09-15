package me.sulaxan.zombies.game;

import lombok.Setter;
import me.sulaxan.zombies.game.object.ObjectManager;
import me.sulaxan.zombies.game.state.*;
import me.sulaxan.zombies.game.state.result.StateGameOver;
import me.sulaxan.zombies.game.state.result.StateWin;
import me.sulaxan.zombies.game.state.tutorial.StateControls;
import me.sulaxan.zombies.game.state.tutorial.StateHowToPlay;
import me.sulaxan.zombies.util.component.ComponentManager;
import me.sulaxan.zombies.game.object.entity.impl.player.ControlManager;
import me.sulaxan.zombies.game.object.entity.impl.player.Player;
import me.sulaxan.zombies.util.gui.Window;
import me.sulaxan.zombies.game.object.entity.impl.player.PlayerCamera;
import me.sulaxan.zombies.util.GameState;
import me.sulaxan.zombies.util.ImageUtil;
import me.sulaxan.zombies.game.world.RoundManager;
import me.sulaxan.zombies.game.world.World;
import lombok.AccessLevel;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Zombies extends Canvas implements Runnable {

    private static Zombies instance;
    @Getter(AccessLevel.PUBLIC) private Thread thread;
    @Setter private boolean running;
    private int frames;
    private long playTime;

    private Window window;
    private List<GameState> gameStates;
    @Getter(AccessLevel.NONE) private int currentState;

    private List<World> worlds;
    @Setter private Player player;
    private PlayerCamera camera;

    private Font defaultFont;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int SCALE = 14;

    private final String version = "v1.1.0";
    private final String subVersion = null;

    private Zombies() {
        instance = this;
        this.thread = new Thread(this);
        this.running = true;
        this.window = new Window("Zombies | Version " + version, WIDTH, HEIGHT, this);
        this.gameStates = new ArrayList<>();
        this.worlds = new ArrayList<>();
        this.player = new Player();
        this.camera = new PlayerCamera(0, 0);

        //TODO: Add asset loader!
        try {
            defaultFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("munro.ttf"));
        } catch (Exception e) {
            System.err.println("Could not load font!");
            return;
        }

        // Adding game states
        gameStates.add(new StateMainMenu());
        gameStates.add(new StateHowToPlay());
        gameStates.add(new StateControls());
        gameStates.add(new StateModeInfoMenu());
        gameStates.add(new StateInfo());
        gameStates.add(new StateGame());
        gameStates.add(new StatePause());
        gameStates.add(new StateWin());
        gameStates.add(new StateGameOver());

        // Temporary world
        worlds.add(new World("Mansion", "world/mansion.png"));
        getCurrentWorld().getEntities().add(player);

        // Logo
        window.getFrame().setIconImage(ImageUtil.loadImageFromJar("logo.png"));

        // Initializes the control manager
        ControlManager.getInstance();

        thread.start();
    }

    /**
     * @return The current state, null if none.
     */
    public GameState getCurrentState() {
        if(currentState < gameStates.size()) {
            return gameStates.get(currentState);
        }
        return null;
    }

    /**
     * Gets the index of a game state using the class object.
     * @param c The class to use to get the index of game state.
     * @return The index of the game state, 0 if none.
     */
    public int getGameStateIndex(Class<? extends GameState> c) {
        int currentIndex = 0;
        for(GameState state : gameStates) {
            if(state.getClass() == c) return currentIndex;
            currentIndex++;
        }
        return 0;
    }

    /**
     * Cycles to the next state.
     */
    public void nextState() {
        setState(currentState + 1);
    }

    /**
     * Sets the current state to the specified integer.
     * States start with 0.
     *
     * @param state The state to set to.
     */
    public void setState(int state) {
        // Previous state
        if(getCurrentState() != null) getCurrentState().onEnd();

        this.currentState = state;

        // Next state
        if(getCurrentState() != null) getCurrentState().onStart();
    }

    /**
     * @return The current world in use.
     */
    public World getCurrentWorld() {
        return worlds.get(0);
    }

    /**
     * Updates the player camera and all worlds.
     */
    private void tick() {
        ComponentManager.getInstance().tick();
        ObjectManager.getInstance().tick();
        GameState state = getCurrentState();
        if(state != null) state.tick();
    }

    /**
     * Gets the current buffer (creates a buffer of 3 if current is null),
     * and gets both Graphics and Graphics2D. Once the graphics are received,
     * the camera positioning is translated onto the screen and all worlds
     * are rendered.
     */
    private void render() {
        BufferStrategy strategy = this.getBufferStrategy();
        if(strategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = strategy.getDrawGraphics();
        // Below is rendering stuff
        ComponentManager.getInstance().render(g);
        ObjectManager.getInstance().render(g);

        GameState state = getCurrentState();
        if(state != null) state.render(g);

        // Disposing the graphics and showing the buffer
        g.dispose();
        strategy.show();
    }

    /**
     * Stops the current game, and joins all threads.
     */
    public void stop() {
        this.running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isPaused() {
        if(getCurrentState() != null && getCurrentState() instanceof StatePause) return true;
        return false;
    }

    public void setPaused(boolean paused) {
        if(getCurrentState() != null && (getCurrentState() instanceof StateGame || getCurrentState() instanceof StatePause)) {
            if(paused) {
                setState(getGameStateIndex(StatePause.class));
            } else {
                setState(getGameStateIndex(StateGame.class));
            }
        }
    }

    public void reset() {
        getCurrentWorld().removeEntity(player);
        getCurrentWorld().spawnEntity(player = new Player());
        RoundManager.getInstance().resetRounds();
    }

    /**
     * Runs the timing for the game.
     */
    @Override
    public void run() {
        if(getCurrentState() != null) getCurrentState().onStart();
        this.requestFocus();

        long lastTime = System.nanoTime();
        double fps = 30.0;
        double nanoSeconds = 1_000_000_000 / fps;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSeconds;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                playTime += 1000;
                frames = 0;
            }
        }
    }

    /**
     * Gets the current instance of the object, creates a new
     * object if the current is null.
     * @return Singleton instance of Zombies.
     */
    public static Zombies getInstance() {
        if(instance == null) instance = new Zombies();
        return instance;
    }
}
