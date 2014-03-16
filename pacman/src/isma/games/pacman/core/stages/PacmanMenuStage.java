package isma.games.pacman.core.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import isma.games.pacman.core.actors.TextActor;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.screens.GameScreen;

import static isma.games.pacman.core.screens.GameScreen.GameType.DEMO;
import static isma.games.pacman.core.screens.GameScreen.GameType.NORMAL;

public class PacmanMenuStage extends Stage {
    private final Game game;

    public PacmanMenuStage(Game game) {
        this.game = game;
        addActor(new Image(Assets.TEXTURE_MENU_BACKGROUND));
        addActor(new TextActor(Assets.FONT_PACMAN_64, "pacman", Gdx.graphics.getHeight() - 50));
        addActor(buildTable());

    }

    @Override
    public void act() {

    }

    private Table buildTable() {
        TextButton startButton = new TextButton("Start", Assets.skin);
        TextButton demoButton = new TextButton("Demo", Assets.skin);
        TextButton exitButton = new TextButton("Exit", Assets.skin);

        Table table = new Table(Assets.skin);
        table.setFillParent(true);
        table.add(startButton).width(150).height(50);
        table.row();
        table.add(demoButton).width(150).height(50).padTop(10);
        table.row();
        table.add(exitButton).width(150).height(50).padTop(10);

        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.soundManager.stopMusic();
                game.setScreen(new GameScreen(game, NORMAL));
                return true;
            }
        });
        demoButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.soundManager.stopMusic();
                game.setScreen(new GameScreen(game, DEMO));
                return true;
            }
        });
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return table;
    }
}
