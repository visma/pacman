package isma.games.pacman.core.stages;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import isma.games.Direction;
import isma.games.Input;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.ai.GhostAIMoveHandler;
import isma.games.pacman.core.ai.PacmanAIMoveHandler;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.manager.ActorStateManager;
import isma.games.pacman.core.manager.DefaultMoveHandler;
import isma.games.pacman.core.manager.MoveManager;
import isma.games.pacman.core.screens.MainMenuScreen;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;
import static isma.games.pacman.core.screens.GameScreen.GameType;

public class PacmanStage extends Stage implements DirectionListener {
    private static final FPSLogger FPS_LOGGER = new FPSLogger();
    private final Game game;

    private final PacmanWorld world;
    private final MoveManager moveManager = new MoveManager();
    private final ActorStateManager actorStateManager = new ActorStateManager();

    public PacmanStage(Game game, Maze maze, GameType gameType) {
        this.game = game;
        Gdx.app.setLogLevel(Application.LOG_INFO);

        world = new PacmanWorld(maze);
        addAllActors();

        switch (Gdx.app.getType()) {
            case Android:
                Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener(this)));
                break;
            case Desktop:
                Gdx.input.setInputProcessor(this);
                addListener(new InnerInputListener());
                break;
        }

        if (gameType == GameType.NORMAL) {
            moveManager.addHandler(world.pacman, new DefaultMoveHandler());
        } else {
            moveManager.addHandler(world.pacman, new PacmanAIMoveHandler(world));
        }


        world.restart(true);
    }

    static int act = 0;
    //static long startTime = TimeUtils.millis();

    @Override
    public void act(float delta) {
        //super.act(delta);
        FPS_LOGGER.log();
        if (Assets.configuration.showFps() && (act++ % 10) == 0) {
            world.gameBoard.setMessage("FPS : " + Gdx.graphics.getFramesPerSecond());
        }
        if (!world.ready) {
            return;
        }
        moveManager.moveAll(delta, world.maze);
        actorStateManager.handleState(world);
    }


    private void addAllActors() {
        for (Food food : world.dotMap.values()) {
            addActor(food);
        }
        addActor(world.pacman);
        for (int i = 0; i < world.ghosts.size; i++) {
            Ghost ghost = world.ghosts.get(i);
            addActor(ghost);
            moveManager.addHandler(ghost, new GhostAIMoveHandler(ghost, world));
        }
        addActor(world.debugPath);
        addActor(world.gameBoard);
        addActor(world.ghostPoints);
        addActor(world.fruitPoints);
        addActor(world.fruit);
    }


    /**
     * ************************************************************
     * INPUTS - GESTURES
     * *************************************************************
     */
    @Override
    public void onLeft() {
        onInput(WEST);
    }

    @Override
    public void onRight() {
        onInput(EAST);
    }

    @Override
    public void onUp() {
        onInput(NORTH);
    }

    @Override
    public void onDown() {
        onInput(SOUTH);
    }

    private void onInput(Direction direction) {
        moveManager.getHandler(this.world.pacman).setDirection(direction);
    }

    /**
     * ************************************************************
     * INPUTS - KEYBOARD
     * *************************************************************
     */

    private class InnerInputListener extends InputListener {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (Input.get(keycode)) {
                case UP:
                    onUp();
                    break;
                case DOWN:
                    onDown();
                    break;
                case LEFT:
                    onLeft();
                    break;
                case RIGHT:
                    onRight();
                    break;
                case ESCAPE:
                    if (world.ready) {
                        game.setScreen(new MainMenuScreen(game));
                    }
                    break;
                case BACK:
                    if (world.ready) {
                        game.setScreen(new MainMenuScreen(game));
                    }
                    break;
            }
            return true;
        }
    }


    public PacmanWorld getWorld() {
        return world;
    }
}
