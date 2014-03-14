package isma.games.pacman.core.stages;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Timer;
import java.util.TimerTask;

import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Fruit;
import isma.games.pacman.core.actors.FruitPoints;
import isma.games.pacman.core.actors.GameBoard;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.actors.GhostPoints;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.actors.WorldEventListener;
import isma.games.pacman.core.assets.Assets;

import static isma.games.pacman.core.actors.Ghost.GhostState;

public class ScoreManager implements WorldEventListener {
    private static final int DOT_POINT = 10;
    private static final int ENERGIZER_POINTS = 50;
    private static final int BASE_GHOST_POINTS = 100;
    public static final int ENERGIZER_COUNT = 4;

    private final ArrayMap<Integer, Integer> ghostEatedByEnergizer = new ArrayMap<Integer, Integer>();
    int score = 0;
    int energizerConsumed = 0;

    private final GhostPoints ghostPoints;
    private final FruitPoints fruitPoints;
    private final GameBoard gameBoard;

    public ScoreManager(GhostPoints ghostPoints, FruitPoints fruitPoints, GameBoard gameBoard) {
        this.ghostPoints = ghostPoints;
        this.fruitPoints = fruitPoints;
        this.gameBoard = gameBoard;
        resetPoints();
    }


    public void reset(){
        energizerConsumed = 0;
        resetPoints();
    }

    private void resetPoints() {
        for (int i = 1; i <= ENERGIZER_COUNT; i++) {
            ghostEatedByEnergizer.put(i, 0);
        }
    }

    @Override
    public void onConsumed(Food food) {
        if (food instanceof Dot) {
            Dot dot = (Dot) food;
            addPoints(dot.isEnergizer() ? ENERGIZER_POINTS : DOT_POINT);
            if (dot.isEnergizer()) {
                resetPoints();
                energizerConsumed++;
            }
        }else {
            addPoints(((Fruit)food).getCurrentFruit().points);
            fruitPoints.setVisible(true);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    fruitPoints.setVisible(false);
                }
            }, Assets.configuration.getShowPointsDuration());
        }
    }

    @Override
    public void onConsumed(AliveActor actor) {
        if (actor instanceof Pacman) {
            return;
        }
        final int ghostCount = ghostEatedByEnergizer.get(energizerConsumed) + 1;
        ghostEatedByEnergizer.put(energizerConsumed, ghostCount);
        final Vector2 ghostPosition = new Vector2(actor.getX(), actor.getY());

        ghostPoints.add(ghostCount, ghostPosition);
        addPoints(Math.pow(2, ghostCount) * BASE_GHOST_POINTS);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ghostPoints.remove(ghostPosition);
            }
        }, Assets.configuration.getShowPointsDuration());

    }

    private void addPoints(double points) {
        score += points;
        gameBoard.setScore((long) score);
    }

    @Override
    public void onStateChanged(Ghost ghost, GhostState oldState, GhostState newState) {
    }
}
