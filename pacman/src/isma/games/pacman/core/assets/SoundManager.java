package isma.games.pacman.core.assets;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private boolean enabled = false;

    public static final float PITCH = 1.0f;
    public static final float PAN = 0f;

    private static final float VOLUME = 0.2f;

    private final Sound intro;
    private final Sound chompDot;
    private final Sound chompEnergizer;
    private final Sound chompGhost;
    private final Sound death;

    public SoundManager() {
        intro = Assets.MUSIC_INTRO;
        chompDot = Assets.SOUND_CHOMP_DOT;
        chompGhost = Assets.SOUND_CHOMP_EATED;
        chompEnergizer = Assets.SOUND_CHOMP_ENERGIZER;
        death = Assets.SOUND_DEATH;
    }

    public void playIntro() {
        playSound(intro, false);
    }

    public void playChompDot() {
        playSound(chompDot, false);
    }

    public void playChompGhost() {
        playSound(chompGhost, false);
    }

    public void playChompEnergizer() {
        playSound(chompEnergizer, true);
    }

    public void playDeath() {
        playSound(death, false);
    }

    public void stopChompEnergizer() {
        stopSound(chompEnergizer);
    }

    private void stopSound(Sound chompEnergizer1) {
        if (enabled) {
            chompEnergizer1.stop();
        }
    }

    private void playSound(Sound sound, boolean loop) {
        if (enabled) {
            long id = sound.play(VOLUME, PITCH, PAN);
            sound.setLooping(id, loop);
        }
    }
}
