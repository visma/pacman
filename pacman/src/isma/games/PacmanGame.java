package isma.games;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.screens.GameScreen;
import isma.games.pacman.core.screens.MainMenuScreen;
import isma.games.pacman.core.screens.SplashScreen;

public class PacmanGame extends Game {

    @Override
    public void create() {
        Assets.load();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
//        setScreen(new SplashScreen(this));
        setScreen(new MainMenuScreen(this));
//        setScreen(new GameScreen(this));
    }


}
