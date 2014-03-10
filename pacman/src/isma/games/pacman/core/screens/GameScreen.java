package isma.games.pacman.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import isma.games.pacman.core.stages.PacmanStage;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeFactory;

public class GameScreen implements Screen {
    private Game game;

    private Maze maze;
    private PacmanStage stage;
    private OrthographicCamera camera;


    public GameScreen(Game game) {
        this.game = game;
        camera = new OrthographicCamera(Maze.WIDTH, Maze.HEIGHT);
        maze = MazeFactory.buildMaze(camera);
        stage = new PacmanStage(game, maze);


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
        camera.position.set(Maze.WIDTH / 2, Maze.HEIGHT / 2, 0);
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
        stage.dispose();
    }


}