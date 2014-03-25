package isma.games.pacman.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.stages.PacmanStage;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeFactory;

public class GameScreen implements Screen {
    private final float zoom;

    private final Game game;
    private final Maze maze;
    private final PacmanStage stage;
    private final OrthographicCamera camera;

    public enum GameType{
        DEMO,
        NORMAL
    }

    public GameScreen(Game game, GameType gameType) {
        this.game = game;

        zoom = Assets.configuration.getScaleRatio();
        camera = buildCamera();
        maze = MazeFactory.buildMaze();
        stage = new PacmanStage(game, maze, gameType);
        stage.setCamera(camera);
    }

    private OrthographicCamera buildCamera() {
        int width = Gdx.app.getGraphics().getWidth();
        int height = Gdx.app.getGraphics().getHeight();

        float mazeRatio = (float) Maze.WIDTH / (float) Maze.HEIGHT;
        float screenRatio = (float) width / (float) height;

        float viewPortYOffset = 0;
        float viewPortXOffset = 0;
        if (screenRatio < mazeRatio) {
            //too much height
            viewPortYOffset = (mazeRatio - screenRatio) * Maze.HEIGHT;
        } else {
            viewPortXOffset = (screenRatio - mazeRatio) * Maze.WIDTH;
        }
        return new OrthographicCamera(
                (Maze.WIDTH + viewPortXOffset) * zoom,
                (Maze.HEIGHT + viewPortYOffset) * zoom);
    }


    @Override
    public void render(float delta) {
        if (stage.getWorld().isGameOver()) {
            game.setScreen(new MainMenuScreen(game));
        } else {
            stage.act(Gdx.graphics.getDeltaTime());
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            maze.render(camera);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.position.set(Maze.WIDTH * zoom / 2, Maze.HEIGHT * zoom / 2, 0);
        camera.update();
    }

    @Override
    public void show() {
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
        Gdx.app.log("LIFE_CYCLE", "GameScreen.dispose()");
        stage.dispose();
        maze.dispose();
    }


}