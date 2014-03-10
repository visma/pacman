package isma.games.pacman.core.assets;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {
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
        long id = intro.play(VOLUME, 1.0f, 0f);
        intro.setLooping(id, false);
    }

    public void playChompDot() {
        long id = chompDot.play(VOLUME, 1.0f, 0f);
        chompDot.setLooping(id, false);
    }

    public void playChompGhost() {
        long id = chompGhost.play(VOLUME, 1.0f, 0f);
        chompGhost.setLooping(id, false);
    }

    public void playChompEnergizer() {
        long id = chompEnergizer.play(VOLUME, 1.0f, 0f);
        chompEnergizer.setLooping(id, true);
    }

    public void playDeath() {
        long id = death.play(VOLUME, 1.0f, 0f);
        death.setLooping(id, false);
    }

    public void stopChompEnergizer() {
        chompEnergizer.stop();
    }
}
