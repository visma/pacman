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
import isma.games.pacman.core.manager.ActorStateManager;
import isma.games.pacman.core.manager.DefaultMoveHandler;
import isma.games.pacman.core.manager.MoveHandler;
import isma.games.pacman.core.manager.MoveManager;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;

public class PacmanStage extends Stage implements DirectionListener {
    private static final FPSLogger FPS_LOGGER = new FPSLogger();

    private PacmanWorld world;
    private MoveManager moveManager = new MoveManager();
    private ActorStateManager actorStateManager = new ActorStateManager();

    public PacmanStage(Game game, Maze maze) {
        Gdx.app.setLogLevel(Application.LOG_INFO);

        world = new PacmanWorld(game, maze);
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

        MoveHandler moveHandler = new DefaultMoveHandler();
//        MoveHandler moveHandler = new PacmanAIMoveHandler(world);
        moveManager.addHandler(world.pacman, moveHandler);


        world.restart(true);
    }

    static int act = 0;

    @Override
    public void act(float delta) {
        //super.act(delta);
        //FPS_LOGGER.log();
        if ((act++ % 10) == 0) {
            world.gameBoard.setMessage("FPS : " + Gdx.graphics.getFramesPerSecond());
        }
        if (!world.ready) {
            return;
        }
        moveManager.moveAll(delta, world.maze);
        actorStateManager.handleState(world);

//        if (timeSinceMillis(startTime) > 120 * 1000) {
//            System.out.println("minute finie : " + timeSinceMillis(startTime) / 1000);
//            System.exit(0);
//        }
    }


    private void addAllActors() {
        for (Food food : world.foodMap.values()) {
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
        PacmanWorld world = PacmanStage.this.world;
        if (!world.ready) {
            return;
        }
        moveManager.getHandler(world.pacman).setDirection(direction);
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
            }
            return true;
        }
    }


    @Override
    public void dispose() {
        super.dispose();
        world.maze.dispose();
    }

    public PacmanWorld getWorld() {
        return world;
    }
}
