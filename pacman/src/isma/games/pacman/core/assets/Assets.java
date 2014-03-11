package isma.games.pacman.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.Gdx.files;

//TODO faire un cache de resources
public class Assets {
    //TODO bouger speeds
    public static final float SPEED_GHOST = 0.85f;
    public static final float SPEED_PACMAN = 1.00f;

    public static final int MAIN_LAYER = 0;
    public static final String PACMAN_TILEDMAP_FILEPATH = "tiledmap/pacman_level.tmx";

    /**
     * ***************************************************
     * SPRITES
     * ****************************************************
     */
    private static final String SPRITE_SHEET_ALIVE_ACTOR = "sprites/sprite_sheet_%s.png";
    private static final String SPRITE_SHEET_ALIVE_ACTOR_CORE = "sprites/sprite_sheet_%s_core.png";

    private static final String SPRITE_SHEET_GHOST_FRIGHTENED = "sprites/sprite_sheet_frightened.png";
    private static final String SPRITE_SHEET_GHOST_NAKED = "sprites/sprite_sheet_naked.png";

    private static final String SPRITE_SHEET_PACMAN_DEATH = "sprites/sprite_sheet_pacman_death.png";

    private static final String SPRITE_SHEET_DOT = "sprites/sprite_sheet_dot.png";
    private static final String SPRITE_SHEET_ENERGIZER = "sprites/sprite_sheet_energizer.png";

    private static final String SPRITE_SHEET_DEBUG_PATH_TRACE = "sprites/sprite_sheet_%s_path_trace.png";
    private static final String SPRITE_SHEET_DEBUG_GRAPH_VERTEXES = "sprites/sprite_sheet_yellow_square.png";

    /**
     * ***************************************************
     * SOUNDS
     * ****************************************************
     */
    private static final String MUSIC_INTRO_RES = "sounds/intro.ogg";
    private static final String SOUND_CHOMP_DOT_RES = "sounds/munch_A.ogg";
    private static final String SOUND_CHOMP_ENERGIZER_RES = "sounds/large_pellet_loop.ogg";
    private static final String SOUND_CHOMP_EATED_RES = "sounds/ghost_eat_7.ogg";
    private static final String SOUND_DEATH_RES = "sounds/death_1.ogg";

    public static Texture backgroundTexture;
    public static Skin skin;

    public static final Texture TEXTURE_DOT;
    public static final Texture TEXTURE_ENERGIZER;
    public static final Texture TEXTURE_GHOST_FRIGHTENED;
    public static final Texture TEXTURE_GHOST_NAKED;
    public static final Texture TEXTURE_PACMAN_DEATH;
    public static final Texture TEXTURE_DEBUG_GRAPH_VERTEXES;

    public static final Sound MUSIC_INTRO;
    public static final Sound SOUND_CHOMP_DOT;
    public static final Sound SOUND_CHOMP_ENERGIZER;
    public static final Sound SOUND_CHOMP_EATED;
    public static final Sound SOUND_DEATH;



    static {
        TEXTURE_DOT = new Texture(files.internal(Assets.SPRITE_SHEET_DOT));
        TEXTURE_ENERGIZER = new Texture(files.internal(Assets.SPRITE_SHEET_ENERGIZER));
        TEXTURE_GHOST_FRIGHTENED = new Texture(files.internal(Assets.SPRITE_SHEET_GHOST_FRIGHTENED));
        TEXTURE_GHOST_NAKED = new Texture(files.internal(Assets.SPRITE_SHEET_GHOST_NAKED));
        TEXTURE_PACMAN_DEATH = new Texture(files.internal(Assets.SPRITE_SHEET_PACMAN_DEATH));
        TEXTURE_DEBUG_GRAPH_VERTEXES = new Texture(files.internal(Assets.SPRITE_SHEET_DEBUG_GRAPH_VERTEXES));

        MUSIC_INTRO = Gdx.audio.newSound(files.internal(MUSIC_INTRO_RES));
        SOUND_CHOMP_DOT = Gdx.audio.newSound(files.internal(SOUND_CHOMP_DOT_RES));
        SOUND_CHOMP_ENERGIZER = Gdx.audio.newSound(files.internal(SOUND_CHOMP_ENERGIZER_RES));
        SOUND_CHOMP_EATED = Gdx.audio.newSound(files.internal(SOUND_CHOMP_EATED_RES));
        SOUND_DEATH = Gdx.audio.newSound(files.internal(SOUND_DEATH_RES));
    }

    public static void load() {
        skin = SkinFactory.buildSkin();
        backgroundTexture = skin.get(SkinFactory.SPLASHSCREEN_KEY, Texture.class);

    }

    public static String getAliveActorSpriteSheet(String id) {
        //return format(SPRITE_SHEET_ALIVE_ACTOR, id);
        return SPRITE_SHEET_ALIVE_ACTOR.replace("%s", id);
    }

    public static String getPathTraceSpriteSheet(String actorId) {
        return SPRITE_SHEET_DEBUG_PATH_TRACE.replace("%s", actorId);
//        return format(SPRITE_SHEET_DEBUG_PATH_TRACE, actorId);
    }
}
