package isma.games.pacman.core.stages;

import com.badlogic.gdx.Game;
import isma.games.Point;
import isma.games.pacman.core.actors.*;
import isma.games.pacman.core.assets.SoundManager;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeBuilder;

import java.util.*;

import static isma.games.pacman.core.actors.Ghost.GhostState.FRIGTHENED;
import static isma.games.pacman.core.actors.Pacman.PacmanState.DYING;
import static isma.games.pacman.core.stages.PacmanWorld.GameState.GAME_OVER;
import static isma.games.pacman.core.stages.PacmanWorld.GameState.RUNNING;

public class PacmanWorld implements WorldContainer {
    private Game game;

    private static final int RESTART_DURATION = 3000;
    private static final int GHOST_EAT_DURATION = 700;
    private static final int DYING_DURATION = 2000;

    private final SoundManager soundManager;

    final GameBoard gameBoard;
    final Maze maze;

    final Map<Point, Food> foodMap;
    final Pacman pacman;
    final List<Ghost> ghosts = new ArrayList<Ghost>();
    final DebugPath debugPath;

    int level;
    int lives;
    int points;

    boolean ready;
    GameState state;

    enum GameState {
        RUNNING,
        GAME_OVER
    }


    public PacmanWorld(Game game, Maze maze) {
        this.game = game;
        soundManager = new SoundManager();

        gameBoard = new GameBoard();
        this.maze = maze;
        foodMap = MazeBuilder.loadFood(maze);
        pacman = ActorFactory.buildPacman();
        debugPath = new DebugPath(false);

        //ghosts.addAll(ActorFactory.buildAllGhosts());
        ghosts.add(ActorFactory.buildPinky());
        ghosts.add(ActorFactory.buildClyde());
        ghosts.add(ActorFactory.buildBlinky());
        ghosts.add(ActorFactory.buildInky());

        addWorldEventListeners();
        state = RUNNING;
        level = 1;
        lives = 300;
        points = 0;
    }

    private void addWorldEventListeners() {
        for (Food food : foodMap.values()) {
            food.addWorldEventListeners(this);
        }
        for (Ghost ghost : ghosts) {
            ghost.addWorldEventListeners(this);
        }
        pacman.addWorldEventListeners(this);
    }


    public void restart(boolean firstTry) {
        ready = false;
        ActorBuilder.resetAll(pacman, ghosts);
        gameBoard.setMessage("ROUND " + level);
        if (firstTry) {
            soundManager.playIntro();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ready = true;
                gameBoard.setMessage(null);
            }
        }, RESTART_DURATION);
    }

    public void nextLevel() {
        level++;
        for (Food food : foodMap.values()) {
            if (food instanceof Dot) {
                Dot dot = (Dot) food;
                dot.setAlive(true);
            }
        }
        restart(false);
    }

    private void handleGameOver() {
        pacman.setVisible(false);
        gameBoard.setMessage("GAME OVER");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ready = true;
                state = GAME_OVER;
                gameBoard.setMessage(null);
            }
        }, RESTART_DURATION);

    }

    @Override
    public void onConsumed(AliveActor actor) {
        if (actor instanceof Ghost) {
            soundManager.playChompGhost();
            freeze(GHOST_EAT_DURATION);
        } else if (actor instanceof Pacman) {
            handlePacmanAfterDeath();
        }
    }

    private void handlePacmanAfterDeath() {
        lives--;
        soundManager.playDeath();
        pacman.setState(DYING);
        for (Ghost ghost : ghosts) {
            ghost.setVisible(false);
        }
        ready = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (lives > 0) {
                    restart(false);
                } else {
                    handleGameOver();
                }
            }
        }, DYING_DURATION);
    }


    private void freeze(long millis) {
        ready = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ready = true;
            }
        }, millis);
    }

    public void onConsumed(Food food) {
        if (food instanceof Dot) {
            Dot dot = (Dot) food;
            if (!dot.isEnergizer()) {
                soundManager.playChompDot();
            } else {
                soundManager.playChompEnergizer();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean frightenedGhost = true;
                        while (frightenedGhost) {
                            frightenedGhost = false;
                            for (Ghost ghost : ghosts) {
                                if (ghost.getState() == FRIGTHENED) {
                                    frightenedGhost = true;
                                    break;
                                }
                                frightenedGhost = false;
                            }
                        }
                        soundManager.stopChompEnergizer();
                    }
                }).start();
            }
        }
        if (getRemainingFood().isEmpty()) {
            nextLevel();
        }
    }

    public boolean isGameOver() {
        return state == GAME_OVER;
    }

    /**
     * *****************************************************************************************
     * WORLD CONTAINER INTERFACE
     * ******************************************************************************************
     */
    @Override
    public Food getFoodAt(Point position) {
        return foodMap.get(position);
    }

    @Override
    public List<Food> getRemainingFood() {
        List<Food> remaining = new ArrayList<Food>();
        for (Food food : foodMap.values()) {
            if (food.isAlive()) {
                remaining.add(food);
            }
        }
        return remaining;
    }


    @Override
    public Pacman getPacman() {
        return pacman;
    }

    @Override
    public List<Ghost> getGhosts() {
        return ghosts;
    }

    @Override
    public Ghost getGhost(String id) {
        for (Ghost ghost : ghosts) {
            if (ghost.getId().equals(id)) {
                return ghost;
            }
        }
        return null;
    }

    public Maze getMaze() {
        return maze;
    }

    public DebugPath getDebugPath() {
        return debugPath;
    }

    @Override
    public int getLevel() {
        return level;
    }


}
