package isma.games.pacman.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import isma.games.pacman.core.conf.PacmanGameConfiguration;

import static com.badlogic.gdx.Gdx.files;

public class Assets {
    public static PacmanGameConfiguration configuration = new PacmanGameConfiguration();

    public static SoundManager soundManager;

    private static Array<Texture> textures = new Array<Texture>();
    private static Array<Music> musics = new Array<Music>();
    private static Array<Sound> sounds = new Array<Sound>();
    private static Array<BitmapFont> fonts = new Array<BitmapFont>();


    public static final int MAIN_LAYER = 0;
    public static final String PACMAN_TILEDMAP_FILEPATH = "tiledmap/pacman_level.tmx";

    /**
     * ***************************************************
     * IMAGES
     * ****************************************************
     */
    private static final String SPRITE_SHEET_ALIVE_ACTOR = "sprites/sprite_sheet_%s.png";
    private static final String SPRITE_SHEET_ALIVE_ACTOR_CORE = "sprites/sprite_sheet_%s_core.png";
    private static final String SPRITE_SHEET_DEBUG_PATH_TRACE = "sprites/sprite_sheet_%s_path_trace.png";

//    public static Texture backgroundTexture;
    public static Skin skin;

    public static Texture TEXTURE_MENU_BACKGROUND;

    public static Texture TEXTURE_DOT;
    public static Texture TEXTURE_FRUITS;
    public static Texture TEXTURE_ENERGIZER;
    public static Texture TEXTURE_GHOST_FRIGHTENED;
    public static Texture TEXTURE_GHOST_NAKED;
    public static Texture TEXTURE_PACMAN_DEATH;
    public static Texture TEXTURE_GHOST_POINTS;
    public static Texture TEXTURE_FRUIT_POINTS;

    public static Texture TEXTURE_DEBUG_GRAPH_VERTEXES;

    /**
     * ***************************************************
     * FONTS
     * ****************************************************
     */
    public static BitmapFont FONT_PACMAN_32;
    public static BitmapFont FONT_PACMAN_64;
    public static BitmapFont FONT_ARCADE_12;

    /**
     * ***************************************************
     * SOUNDS
     * ****************************************************
     */
    public static Music MUSIC_MENU;
    public static Music MUSIC_INTRO;

    public static Music MUSIC_DEFAULT;
    public static Music MUSIC_NAKED;
    public static Music MUSIC_FRIGHTENED;

    public static Sound SOUND_CHOMP_DOT;
    public static Sound SOUND_CHOMP_DOT_B;

    public static Sound SOUND_CHOMP_FRUIT;
    public static Sound SOUND_CHOMP_GHOST;
    public static Sound SOUND_DEATH;



    public static String getAliveActorSpriteSheet(String id) {
        //TODO a gerer dans texture apres
        return SPRITE_SHEET_ALIVE_ACTOR.replace("%s", id);
    }

    public static String getPathTraceSpriteSheet(String actorId) {
        //TODO a gerer dans texture apres
        return SPRITE_SHEET_DEBUG_PATH_TRACE.replace("%s", actorId);
    }

    private static BitmapFont buildFont(String font) {
        Gdx.app.log("LOADING", "load font : " + font);
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font));
        fonts.add(bitmapFont);
        return bitmapFont;
    }

    private static Sound buildSound(String soundRes) {
        Gdx.app.log("LOADING", "load sound : " + soundRes);
        Sound sound = Gdx.audio.newSound(files.internal(soundRes));
        sounds.add(sound);
        return sound;
    }

    private static Music buildMusic(String musicRes) {
        Gdx.app.log("LOADING", "load sound : " + musicRes);
        Music music = Gdx.audio.newMusic(files.internal(musicRes));
        musics.add(music);
        return music;
    }

    private static Texture buildTexture(String textureRes) {
        Gdx.app.log("LOADING", "load texture : " + textureRes);
        Texture texture = new Texture(files.internal(textureRes));
        textures.add(texture);
        return texture;
    }

    public static void loadAll() {
        TEXTURE_MENU_BACKGROUND = buildTexture("background_arcade.png");
        TEXTURE_DOT = buildTexture("sprites/sprite_sheet_dot.png");
        TEXTURE_FRUITS = buildTexture("sprites/sprite_sheet_fruits.png");
        TEXTURE_ENERGIZER = buildTexture("sprites/sprite_sheet_energizer.png");
        TEXTURE_GHOST_FRIGHTENED = buildTexture("sprites/sprite_sheet_frightened.png");
        TEXTURE_GHOST_NAKED = buildTexture("sprites/sprite_sheet_naked.png");
        TEXTURE_PACMAN_DEATH = buildTexture("sprites/sprite_sheet_pacman_death.png");
        TEXTURE_GHOST_POINTS = buildTexture("sprites/sprite_sheet_ghost_points.png");
        TEXTURE_FRUIT_POINTS = buildTexture("sprites/sprite_sheet_fruits_points.png");

        TEXTURE_DEBUG_GRAPH_VERTEXES = buildTexture("sprites/sprite_sheet_yellow_square.png");

        MUSIC_MENU = buildMusic("sounds/menu.mp3");
        MUSIC_DEFAULT = buildMusic("sounds/siren_slow_3.ogg");
        MUSIC_NAKED = buildMusic("sounds/siren_fast_1.ogg");
        MUSIC_FRIGHTENED = buildMusic("sounds/large_pellet_loop.ogg");
        MUSIC_INTRO = buildMusic("sounds/intro.ogg");

        SOUND_CHOMP_DOT = buildSound("sounds/munch_A.ogg");
        SOUND_CHOMP_DOT_B = buildSound("sounds/munch_B.ogg");
        SOUND_CHOMP_FRUIT = buildSound("sounds/fruit.ogg");
        SOUND_CHOMP_GHOST = buildSound("sounds/ghost_eat_7.ogg");
        SOUND_DEATH = buildSound("sounds/death_1.ogg");

        FONT_ARCADE_12 = buildFont("arcade_12.fnt");
        FONT_PACMAN_32 = buildFont("pacfont_32.fnt");
        FONT_PACMAN_64 = buildFont("pacfont_64.fnt");


        configuration = new PacmanGameConfiguration();
        configuration.load();

        soundManager = new SoundManager(
                configuration.isSoundEnabled(),
                configuration.getSoundVolume());

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    }


    public static void disposeAll() {
        Gdx.app.log("FREE", "stop music");
        soundManager.stopMusic();
        for (Sound sound : sounds) {
            Gdx.app.log("FREE", "sound dispose");
            sound.dispose();
        }
        for (Music music : musics) {
            Gdx.app.log("FREE", "music dispose");
            music.dispose();
        }

        Gdx.app.log("FREE", "skin dispose");
        skin.dispose();

        for (BitmapFont font : fonts) {
            Gdx.app.log("FREE", "font dispose");
            font.dispose();
        }
        for (Texture texture : textures) {
            Gdx.app.log("FREE", "texture dispose");
            texture.dispose();
        }
    }
}
