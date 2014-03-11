package isma.games.pacman.core.ai.behavior;

import isma.games.*;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static isma.games.Log.warn;
import static isma.games.TiledMapHelper.*;
import static isma.games.pacman.core.actors.Ghost.GhostState;
import static isma.games.pacman.core.actors.Ghost.GhostState.*;

public class FearBehavior extends GhostBehavior {

    public FearBehavior(Ghost ghost) {
        super(ghost, 5000);
        ghost.setFrightened(true);
    }

    @Override
    public boolean isOver(WorldContainer stage) {
        return ghost.getState() != FRIGTHENED || super.isOver(stage);
    }

    public Target searchTarget(WorldContainer stage) {
        Maze maze = stage.getMaze();
        Point ghostPosition = getGridPosition(maze, ghost);

        Map<Direction, Point> possibleRuns = new HashMap<Direction, Point>();
        for (Direction direction : Direction.values()) {
            Point nextPosition = ghostPosition.onNext(direction);
            handleOutOfBounds(maze, nextPosition);
            if (maze.isPath(nextPosition)) {
                possibleRuns.put(direction, nextPosition);
            }
        }
        possibleRuns.remove(ghost.getCurrentDirection().opposite());
//        warn("possible moves : " + possibleRuns.size());
        int randIndex = new Random().nextInt(possibleRuns.size());
        int currentIndex = 0;
        Direction randomDirection = null;
        for (Direction direction : possibleRuns.keySet()) {
            if (currentIndex++ == randIndex) {
                randomDirection = direction;
            }
        }
        Point targetPosition = possibleRuns.get(randomDirection);
        while (maze.isPath(targetPosition)) {
            targetPosition = targetPosition.onNext(randomDirection);
        }
        targetPosition = targetPosition.onNext(randomDirection.opposite());
        return TiledMapHelper.getTarget(maze, targetPosition);
    }



    //TODO marche surement pas bien si pacman enchaine vite 2 energizers (la durée n'est pas reseté)
    @Override
    public GhostBehavior nextBehavior(WorldContainer world) {
        return new ChaseGhostBehavior(ghost, ChaseStrategyFactory.build(ghost, world));
    }

    public void update(WorldContainer world) {
        super.update(world);
        if (isOver(world)) {
            if (ghost.getState() == FRIGTHENED) {
                ghost.setFrightened(false);
            }
        }
    }
}
