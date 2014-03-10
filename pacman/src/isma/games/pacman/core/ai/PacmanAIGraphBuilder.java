package isma.games.pacman.core.ai;

import isma.games.Log;
import isma.games.Point;
import isma.games.TargetHelper;
import isma.games.TiledMapHelper;
import isma.games.graph.GraphBuilder;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;

import java.util.Map;

import static isma.games.pacman.core.ai.PacmanAIGraphBuilder.WEIGHT.*;

public class PacmanAIGraphBuilder extends GraphBuilder {
    enum WEIGHT {
        NONE(1),
        NORMAL_DOT(-5),
        ENERGIZER_DOT(-30),
        FRUIT(-30),
        GHOST(200),
        GHOST_FRIGHTENED(-200),
        GHOST_NAKED(10);
        int weight;

        WEIGHT(int weight) {
            this.weight = weight;
        }
    }

    private WorldContainer stage;

    public PacmanAIGraphBuilder(WorldContainer stage) {
        this.stage = stage;
    }

    @Override
    protected int getWeight(Map<Point, Vertex> vertexes, Vertex<Point> source, Vertex<Point> destination) {
        int ghostWeight = getGhostsWeight(destination);
        Food foodAt = stage.getFoodAt(destination.getId());
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
        Log.trace("weight = " + weight);

        return weight;
    }

    private int getGhostsWeight(Vertex<Point> destination) {
        int weight = 0;
        final float dangerRatio = 0.7f;
        int radius = 4;//4 ca avait l air pas mal

        Point destinationPosition = destination.getId();
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
