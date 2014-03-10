package isma.games.pacman.core.stages;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import isma.games.Input;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.ai.GhostAIMoveHandler;
import isma.games.pacman.core.ai.PacmanAIMoveHandler;
import isma.games.pacman.core.manager.ActorStateManager;
import isma.games.pacman.core.manager.DefaultMoveHandler;
import isma.games.pacman.core.manager.MoveHandler;
import isma.games.pacman.core.manager.MoveManager;
import isma.games.pacman.core.screens.MainMenuScreen;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Direction.*;

public class PacmanStage extends Stage {
    private static final FPSLogger FPS_LOGGER = new FPSLogger();

    private PacmanWorld world;
    private MoveManager moveManager = new MoveManager();
    private ActorStateManager actorStateManager = new ActorStateManager();

    public PacmanStage(Game game, Maze maze) {
        Gdx.app.setLogLevel(Application.LOG_INFO);

        world = new PacmanWorld(game, maze);
        addAllActors();

        Gdx.input.setInputProcessor(this);

//        MoveHandler moveHandler = new DefaultMoveHandler();
        MoveHandler moveHandler = new PacmanAIMoveHandler(world);
        moveManager.addHandler(world.pacman, moveHandler);
        addListener(new InnerInputListener(this));

        world.restart(true);
    }

    private void addAllActors() {
        for (Food food : world.foodMap.values()) {
            addActor(food);
        }
        addActor(world.pacman);
        for (Ghost ghost : world.ghosts) {
            addActor(ghost);
            moveManager.addHandler(ghost, new GhostAIMoveHandler(ghost, world));
        }
        addActor(world.debugPath);
        addActor(world.gameBoard);
    }


    /**
     * ************************************************************
     * INPUTS
     * *************************************************************
     */
    private class InnerInputListener extends InputListener {
        private PacmanStage stage;

        private InnerInputListener(PacmanStage stage) {
            this.stage = stage;
        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            PacmanWorld world = PacmanStage.this.world;
            if (!world.ready) {
                return false;
            }
            MoveHandler moveHandler = stage.moveManager.getHandler(world.pacman);
            switch (Input.get(keycode)) {
                case UP:
                    moveHandler.setDirection(NORTH);
                    break;
                case DOWN:
                    moveHandler.setDirection(SOUTH);
                    break;
                case LEFT:
                    moveHandler.setDirection(WEST);
                    break;
                case RIGHT:
                    moveHandler.setDirection(EAST);
                    break;
            }
            return true;
        }
    }


    @Override
    public void act(float delta) {
        //super.act(delta);
//        FPS_LOGGER.log();
        if (!world.ready) {
            return;
        }
        moveManager.moveAll(delta, world.maze);
        actorStateManager.handleState(world);
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
