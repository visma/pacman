package isma.games.pacman.core.conf;


import isma.games.GameConfiguration;

public class PacmanGameConfiguration extends GameConfiguration {
    private int lives;
    private float soundVolume;
    private long soundDurationRestart;
    private long soundDurationGhostEated;
    private long soundDurationDying;
    private boolean soundEnabled;
    private boolean showFps;
    private long showPointsDuration;
    private long fruitDuration;
    private float defaultPacmanSpeed;
    private int scaleRatio;
    private int fpsLimit;
    private long scatterBehaviorDuration;
    private long chaseBehaviorDuration;
    private long fearBehaviorDuration;

    @Override
    public void load() {
        super.load();
        lives = getInt("lives");
        soundVolume = getFloat("sound.volume");
        soundDurationRestart = getLong("sound.duration.restart");
        soundDurationGhostEated = getLong("sound.duration.ghost_eated");
        soundDurationDying = getLong("sound.duration.dying");
        soundEnabled = getBoolean("sound.on");
        showFps = getBoolean("fps.show");
        showPointsDuration = getLong("bonus.duration.showPoints");
        fruitDuration = getLong("fruit.duration");
        defaultPacmanSpeed = getFloat("speed.default.pacman");
        scaleRatio = getInt("scale.ratio");
        fpsLimit = getInt("fps.limit");
        scatterBehaviorDuration = getLong("behavior.scatter.duration");
        chaseBehaviorDuration = getLong("behavior.chase.duration");
        fearBehaviorDuration = getLong("behavior.fear.duration");
    }

    public int getLives() {
        return lives;
    }

    public float getSoundVolume() {
        return soundVolume;
    }


    public long getSoundDurationRestart() {
        return soundDurationRestart;
    }

    public long getSoundDurationGhostEated() {
        return soundDurationGhostEated;
    }

    public long getSoundDurationDying() {
        return soundDurationDying;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }


    public boolean showFps() {
        return showFps;
    }

    public long getShowPointsDuration() {
        return showPointsDuration;
    }

    public long getFruitDuration() {
        return fruitDuration;
    }

    public float getPacmanSpeed() {
        return defaultPacmanSpeed;
    }

    public float getGhostSpeed(String ghostId) {
        return getFloat("speed.default." + ghostId);
    }

    public int getFpsLimit() {
        return fpsLimit;
    }

    public long getScatterBehaviorDuration() {
        return scatterBehaviorDuration;
    }

    public long getChaseBehaviorDuration() {
        return chaseBehaviorDuration;
    }
    public long getFearBehaviorDuration() {
        return fearBehaviorDuration;
    }

    public int getScaleRatio() {
        return scaleRatio;
    }
}
