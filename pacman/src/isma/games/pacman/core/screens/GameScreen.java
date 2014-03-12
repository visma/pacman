package isma.games.pacman.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

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

        buildCamera();
        maze = MazeFactory.buildMaze(camera);
        stage = new PacmanStage(game, maze);
        stage.setCamera(camera);
    }

    private void buildCamera() {
        int width = Gdx.app.getGraphics().getWidth();
        int height = Gdx.app.getGraphics().getHeight();

        float mazeRatio = (float) Maze.WIDTH / (float) Maze.HEIGHT;
        float screenRatio = (float) width / (float) height;

//        System.out.println("mazeRatio = " + mazeRatio);
//        System.out.println("screenRatio = " + screenRatio);

        float viewPortYOffset = 0;
        float viewPortXOffset = 0;
        if (screenRatio < mazeRatio){
            //too much height
            viewPortYOffset = (mazeRatio - screenRatio) * Maze.HEIGHT;
        }else {
            viewPortXOffset = (screenRatio - mazeRatio) * Maze.WIDTH;
        }
        camera = new OrthographicCamera(Maze.WIDTH + viewPortXOffset , Maze.HEIGHT  + viewPortYOffset);
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