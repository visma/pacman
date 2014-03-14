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

import isma.games.pacman.core.assets.Assets;

public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;

    public MainMenuScreen(Game game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = buildTable();
        stage.addActor(new Image(Assets.TEXTURE_MENU_BACKGROUND));
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
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

    private Table buildTable() {
        TextButton startGameButton = new TextButton("New Game", Assets.skin);
        TextButton optionsButton = new TextButton("Options", Assets.skin);
        TextButton exitButton = new TextButton("Exit", Assets.skin);
        Table table = new Table(Assets.skin);
        table.setFillParent(true);
        table.add(startGameButton).width(150).height(50);
        table.row();
        table.add(optionsButton).width(150).height(50).padTop(10);
        table.row();
        table.add(exitButton).width(150).height(50).padTop(10);

        startGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.soundManager.stopMusic();
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return table;
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