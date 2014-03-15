package isma.games;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.screens.MainMenuScreen;

public class PacmanGame extends Game {

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        Assets.loadAll();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.log("LIFE_CYCLE", "PacmanGame.dispose()");

        getScreen().dispose();
        Assets.disposeAll();
        //Gdx.app.exit();
    }
}
