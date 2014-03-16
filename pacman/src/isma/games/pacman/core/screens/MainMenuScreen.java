package isma.games.pacman.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import isma.games.pacman.core.actors.TextActor;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.stages.PacmanMenuStage;

import static isma.games.pacman.core.screens.GameScreen.GameType.DEMO;
import static isma.games.pacman.core.screens.GameScreen.GameType.NORMAL;

public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;

    public MainMenuScreen(Game game) {
        this.game = game;
        stage = new PacmanMenuStage(game);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Assets.soundManager.playMenuMusic();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Gdx.app.log("LIFE_CYCLE", "MainMenuScreen.dispose()");
        Assets.soundManager.stopMusic();
        stage.dispose();
    }

}