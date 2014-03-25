package isma.games.pacman.core.conf;


import isma.games.GameConfiguration;

public class PacmanGameConfiguration extends GameConfiguration {

    public int getLives() {
        return getInt("lives");
    }

    public float getSoundVolume() {
        return getFloat("sound.volume");
    }


    public long getSoundDurationRestart() {
        return getLong("sound.duration.restart");
    }

    public long getSoundDurationGhostEated() {
        return getLong("sound.duration.ghost_eated");
    }

    public long getSoundDurationDying() {
        return getLong("sound.duration.dying");
    }

    public boolean isSoundEnabled() {
        return getBoolean("sound.on");
    }


    public boolean showFps() {
        return getBoolean("fps.show");
    }

    public long getShowPointsDuration() {
        return getLong("bonus.duration.showPoints");
    }

    public long getFruitDuration() {
        return getLong("fruit.duration");
    }

    public float getPacmanSpeed() {
        return getFloat("speed.default.pacman");
    }

    public float getGhostSpeed(String ghostId) {
        return getFloat("speed.default." + ghostId);
    }

    public int getFpsLimit() {
        return getInt("fps.limit");
    }

    public long getScatterBehaviorDuration() {
        return getLong("behavior.scatter.duration");
    }

    public long getChaseBehaviorDuration() {
        return getLong("behavior.chase.duration");
    }
    public long getFearBehaviorDuration() {
        return getLong("behavior.fear.duration");
    }

    public int getScaleRatio() {
        return getInt("scale.ratio");
    }
}
