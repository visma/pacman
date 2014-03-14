package isma.games.pacman.core.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private static final float PITCH = 1.0f;
    private static final float PAN = 0f;

    private final boolean enabled;
    private final float volume;

    private final Music defaultMusic;
    private final Music frightenedMusic;
    private final Music nakedMusic;

    private final Music menuMusic;
    private final Music intro;

    private final Sound death;
    private final Sound chompFruit;
    private final Sound chompGhost;
    private int chompCount = 0;
    private final Sound chompDot;
    private final Sound chompDotB;

    private Music currentlyPlayedMusic;


    public SoundManager(boolean enabled, float volume) {
        this.enabled = enabled;
        this.volume = volume;

        menuMusic = Assets.MUSIC_MENU;
        intro = Assets.MUSIC_INTRO;
        defaultMusic = Assets.MUSIC_DEFAULT;
        frightenedMusic = Assets.MUSIC_FRIGHTENED;
        nakedMusic = Assets.MUSIC_NAKED;

        chompDot = Assets.SOUND_CHOMP_DOT;
        chompDotB = Assets.SOUND_CHOMP_DOT_B;
        chompGhost = Assets.SOUND_CHOMP_GHOST;
        chompFruit = Assets.SOUND_CHOMP_FRUIT;
        death = Assets.SOUND_DEATH;
    }

    /**
     * **********************************************
     * MUSICS
     * ***********************************************
     */
    private void playMusic(Music music, boolean loop) {
        if (enabled) {
            if (currentlyPlayedMusic != null && currentlyPlayedMusic.isPlaying()) {
                currentlyPlayedMusic.stop();
            }
            music.setPan(PAN, volume);
            music.setLooping(loop);
            music.play();
            currentlyPlayedMusic = music;
        }
    }

    public void stopMusic() {
        if (enabled) {
            if (currentlyPlayedMusic != null && currentlyPlayedMusic.isPlaying()) {
                currentlyPlayedMusic.stop();
            }
        }
    }

    public void playMenuMusic() {
        playMusic(menuMusic, true);
    }


    public void playIntroMusic() {
        playMusic(intro, false);
    }

    public void playDefaultMusic() {
        playMusic(defaultMusic, true);
    }

    public void playMusicFrightened() {
        playMusic(frightenedMusic, true);
    }

    public void playNakedMusic() {
        playMusic(nakedMusic, true);
    }

    /**
     * **********************************************
     * SOUNDS
     * ***********************************************
     */
    private void playSound(Sound sound, boolean loop) {
        if (enabled) {
            long id = sound.play(volume, PITCH, PAN);
            sound.setLooping(id, loop);
        }
    }

    public void playChompDot() {
        if (chompCount++ % 2 == 0)
            playSound(chompDot, false);
        else
            playSound(chompDotB, false);
    }

    public void playChompGhost() {
        playSound(chompGhost, false);
    }

    public void playDeath() {
        playSound(death, false);
    }

    public void playChompFruit() {
        playSound(chompFruit, false);
    }
}
