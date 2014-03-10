package isma.games.pacman.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinFactory {

    public static final String SPLASHSCREEN_KEY = "splashscreen";

    private SkinFactory() {
    }

    public static Skin buildSkin() {
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.add(SPLASHSCREEN_KEY, new Texture("background_arcade.png"));
        return skin;
    }
}
