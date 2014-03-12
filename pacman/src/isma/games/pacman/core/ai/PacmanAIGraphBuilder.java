package isma.games.pacman.core.ai;

import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Point;
import isma.games.TargetHelper;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.GraphBuilder;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;

import static isma.games.pacman.core.ai.PacmanAIGraphBuilder.WEIGHT.*;

public class PacmanAIGraphBuilder extends GraphBuilder {
    public final static int ALWAYS_POSITIVE_OFFSET = 0;


    enum WEIGHT {
        GHOST(20 * 1000),
        GHOST_NAKED(2 * 1000),
        NONE(100),
        NORMAL_DOT(10),
        ENERGIZER_DOT(0),
        GHOST_FRIGHTENED(0),
        FRUIT(0);

        int weight;

        WEIGHT(int weight) {
            this.weight = weight + ALWAYS_POSITIVE_OFFSET;
        }
    }

    private WorldContainer stage;

    public PacmanAIGraphBuilder(WorldContainer stage) {
        this.stage = stage;
    }

    @Override
    protected int getWeight(ArrayMap<Point, Vertex<Point>> vertexes, Point destination) {
        int ghostWeight = getGhostsWeight(destination);
        Food foodAt = stage.getFoodAt(destination);
        int foodWeight;
        if (foodAt == null || !foodAt.isAlive()) {
            foodWeight = NONE.weight;
        } else if (foodAt instanceof Dot) {
            Dot dot = (Dot) foodAt;
            if (!dot.isEnergizer()) {
                foodWeight = NORMAL_DOT.weight;
            } else {
                foodWeight = ENERGIZER_DOT.weight;
            }
        } else {
            foodWeight = FRUIT.weight;
        }
        int weight = ghostWeight + foodWeight;
        //Log.warn("weight = " + weight);

        return weight;
    }

    private int getGhostsWeight(Point destinationPosition) {
        int weight = 0;
        final float dangerRatio = 0.7f;
        int radius = 4;//4 ca avait l air pas mal

        for (Ghost ghost : stage.getGhosts()) {
            Point ghostPosition = TiledMapHelper.getGridPosition(stage.getMaze(), ghost);
            if (TargetHelper.hit(destinationPosition, ghostPosition)) {
                weight += getDanger(ghost);
            } else {
                float tileDistance = destinationPosition.dst(ghostPosition);
                if (tileDistance < radius) {
                    float tmpWeight = (dangerRatio * getDanger(ghost)) / tileDistance;
                    weight += tmpWeight;
                }
            }
        }
        return weight;
    }

    private int getDanger(Ghost ghost) {
        switch (ghost.getState()) {
            case NORMAL:
                return GHOST.weight;
            case FRIGTHENED:
                return GHOST_FRIGHTENED.weight;
            case NAKED:
                return GHOST_NAKED.weight;
        }
        throw new RuntimeException("unreachable statement");
    }



}
