package isma.games.pacman.core.stages;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Timer;
import java.util.TimerTask;

import isma.games.Point;
import isma.games.pacman.core.actors.ActorBuilder;
import isma.games.pacman.core.actors.ActorFactory;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.actors.DebugPath;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Fruit;
import isma.games.pacman.core.actors.FruitPoints;
import isma.games.pacman.core.actors.GameBoard;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.actors.GhostPoints;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.ai.PacmanAIGraphBuilder;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.assets.SoundManager;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.rules.FruitRules;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeBuilder;

import static isma.games.pacman.core.actors.Fruit.FruitEnum;
import static isma.games.pacman.core.actors.Ghost.GhostState;
import static isma.games.pacman.core.actors.Ghost.GhostState.FRIGTHENED;
import static isma.games.pacman.core.actors.Ghost.GhostState.NAKED;
import static isma.games.pacman.core.actors.Pacman.PacmanState.DYING;
import static isma.games.pacman.core.stages.PacmanWorld.GameState.GAME_OVER;
import static isma.games.pacman.core.stages.PacmanWorld.GameState.RUNNING;

public class PacmanWorld implements WorldContainer {
    private final SoundManager soundManager;
    private final ScoreManager scoreManager;

    final GameBoard gameBoard;
    final GhostPoints ghostPoints;
    final FruitPoints fruitPoints;

    final Maze maze;

    final ArrayMap<Point, Dot> dotMap;
    final Fruit fruit;
    final Pacman pacman;
    final Array<Ghost> ghosts = new Array<Ghost>();
    final DebugPath debugPath;

    int level;
    int lives;
    int score;

    boolean ready;
    GameState state;

    enum GameState {
        RUNNING,
        GAME_OVER
    }


    public PacmanWorld(Maze maze) {
        this.maze = maze;

        state = RUNNING;
        level = 1;
        lives = Assets.configuration.getLives();
        score = 0;

        dotMap = MazeBuilder.loadDots(maze);
        fruit = MazeBuilder.loadFruit(maze);
        pacman = ActorFactory.buildPacman();

        gameBoard = new GameBoard(lives, fruit);

        debugPath = new DebugPath(new PacmanAIGraphBuilder(this).buildGraph(maze), true, false, false);
        //ghosts.addAll(ActorFactory.buildAllGhosts());
        ghosts.add(ActorFactory.buildBlinky());
        ghosts.add(ActorFactory.buildPinky());
        ghosts.add(ActorFactory.buildClyde());
        ghosts.add(ActorFactory.buildInky());

        ghostPoints = new GhostPoints();
        fruitPoints = new FruitPoints(fruit);

        soundManager = Assets.soundManager;
        scoreManager = new ScoreManager(ghostPoints, fruitPoints, gameBoard);


        addWorldEventListeners();


    }

    private void addWorldEventListeners() {
        fruit.addWorldEventListeners(this);
        fruit.addWorldEventListeners(scoreManager);
        for (Food food : dotMap.values()) {
            food.addWorldEventListeners(this);
            food.addWorldEventListeners(scoreManager);
        }
        for (Ghost ghost : ghosts) {
            ghost.addWorldEventListeners(this);
            ghost.addWorldEventListeners(scoreManager);
        }
        pacman.addWorldEventListeners(this);
        pacman.addWorldEventListeners(scoreManager);
    }


    public void restart(boolean firstTry) {
        ready = false;
        soundManager.stopMusic();
        ActorBuilder.resetAll(pacman, ghosts);
        gameBoard.setMessage("ROUND " + level);
        if (firstTry) {
            soundManager.playIntroMusic();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ready = true;
                gameBoard.setMessage(null);
                soundManager.playDefaultMusic();
            }
        }, Assets.configuration.getSoundDurationRestart());
    }

    public void nextLevel() {
        level++;
        for (Food food : dotMap.values()) {
            if (food instanceof Dot) {
                Dot dot = (Dot) food;
                dot.setAlive(true);
            }
        }
        scoreManager.reset();
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
        }, Assets.configuration.getSoundDurationRestart());

    }

    @Override
    public void onConsumed(AliveActor actor) {
        if (actor instanceof Ghost) {
            soundManager.playChompGhost();
            freeze(Assets.configuration.getSoundDurationGhostEated());
        } else if (actor instanceof Pacman) {
            handlePacmanAfterDeath();
        }
    }

    private void handlePacmanAfterDeath() {
        lives--;
        gameBoard.setLives(lives);
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
        }, Assets.configuration.getSoundDurationDying());
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
                soundManager.playMusicFrightened();
            }
            int remainingDot = getRemainingDots().size;
            if (remainingDot == 0) {
                nextLevel();
            } else {
                if (remainingDot == (dotMap.size - 1 - 70)
                        || remainingDot == dotMap.size - 1 - 170) {
                    showFruit(FruitRules.getFruit(level));
                }
            }
        } else {
            soundManager.playChompFruit();
        }
    }

    private void showFruit(FruitEnum currentFruit) {
        fruit.setCurrentFruit(currentFruit);
        fruit.setAlive(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                fruit.setAlive(false);
            }
        }, Assets.configuration.getFruitDuration());
    }

    @Override
    public void onStateChanged(Ghost aGhost, GhostState old, GhostState state) {
        boolean frightenedGhost = false;
        boolean nakedGhost = false;
        for (Ghost ghost : ghosts) {
            if (ghost.getState() == FRIGTHENED) {
                frightenedGhost = true;
            } else if (ghost.getState() == NAKED) {
                nakedGhost = true;
            }
        }
        if (nakedGhost && frightenedGhost) {
            soundManager.playNakedMusic();
        } else if (frightenedGhost) {
            soundManager.playMusicFrightened();
        } else {
            soundManager.playDefaultMusic();
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
    public Dot getDotAt(Point position) {
        return dotMap.get(position);
    }

    @Override
    public Array<Dot> getRemainingDots() {
        Array<Dot> remaining = new Array<Dot>();
        for (Dot dot : dotMap.values()) {
            if (dot.isAlive()) {
                remaining.add(dot);
            }
        }
        return remaining;
    }

    @Override
    public Food getFruit() {
        return fruit;
    }

    @Override
    public Pacman getPacman() {
        return pacman;
    }

    @Override
    public Array<Ghost> getGhosts() {
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
