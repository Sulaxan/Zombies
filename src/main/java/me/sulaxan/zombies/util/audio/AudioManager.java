package me.sulaxan.zombies.util.audio;

import lombok.Getter;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;

@Getter
public class AudioManager {

    private static AudioManager instance;
    private Map<String, String> sounds;
    private Clip clip;
    private boolean playing;
    private int currentSound;

    // Settings
    private boolean loop;

    private AudioManager() {
        this.sounds = new HashMap<>();
        this.playing = false;
        this.currentSound = 0;
        this.loop = false;

        loadDefaultSounds();

        try {
            this.clip = AudioSystem.getClip();
            AudioInputStream stream = createAudioStream(getAudioFileByIndex(currentSound));
            if(stream != null) clip.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(clip != null) {
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent e) {
                    if(e.getType() == LineEvent.Type.STOP) {
                        if(playing) {
                        }
                    }
                }
            });
        }
    }

    public AudioInputStream getAudioFileByName(String name) {
        for(Map.Entry<String, String> entry : sounds.entrySet()) {
            if(entry.getKey().equalsIgnoreCase(name)) {
                try {
                    return AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(entry.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getAudioFileByIndex(int index) {
        int currentIndex = 0;
        for(Map.Entry<String, String> entry : sounds.entrySet()) {
            if(currentIndex == index) return entry.getValue();
            currentIndex++;
        }
        return null;
    }

    public String getNameByIndex(int index) {
        int currentIndex = 0;
        for(String name : sounds.keySet()) {
            if(currentIndex == index) return name;
            currentIndex++;
        }
        return null;
    }

    public AudioInputStream createAudioStream(String fileName) {
        try {
            BufferedInputStream bufferedStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(fileName));
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedStream);
            return audioStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSound(String name, String file) {
        sounds.put(name, file);
    }

    public void startPlaying() {
        startPlaying(false);
    }

    public void startPlaying(boolean resume) {
        final AudioInputStream stream;
        if(!playing && (stream = createAudioStream(getAudioFileByIndex(currentSound))) != null) {
            try {
                if(!resume) {
                    clip.close();
                    clip.open(stream);
                }
                clip.start();
            } catch(Exception e)  {
                e.printStackTrace();
            }
            playing = true;
        }
    }

    public void stopPlaying() {
        if(playing) {
            clip.stop();
            playing = false;
        }
    }

    private void loadDefaultSounds() {
        addSound("Zelda - Main Theme (30th Ann. Orchestra)", "music/zelda_main_theme.wav");
    }

    public static AudioManager getInstance() {
        if(instance == null) instance = new AudioManager();
        return instance;
    }
}
