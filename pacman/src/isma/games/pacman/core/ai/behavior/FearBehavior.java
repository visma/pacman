package isma.games.pacman.core.ai.behavior;

import com.badlogic.gdx.utils.ArrayMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import isma.games.Direction;
import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.TiledMapHelper.handleOutOfBounds;
import static isma.games.pacman.core.actors.Ghost.GhostState.FRIGTHENED;

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

        ArrayMap<Direction, Point> possibleRuns = new ArrayMap<Direction, Point>();
        for (Direction direction : Direction.values()) {
            Point nextPosition = ghostPosition.onNext(direction);
            nextPosition = handleOutOfBounds(maze, nextPosition);
            if (maze.isPath(nextPosition, PATH_FORCE)) {
                possibleRuns.put(direction, nextPosition);
            }
        }
        if (possibleRuns.size > 1) {
            possibleRuns.removeKey(ghost.getCurrentDirection().opposite());
            //else death end in ghost house
        }
//        warn("possible moves : " + possibleRuns.size());
        int randIndex = new Random().nextInt(possibleRuns.size);
        int currentIndex = 0;
        Direction randomDirection = null;
        for (Direction direction : possibleRuns.keys()) {
            if (currentIndex++ == randIndex) {
                randomDirection = direction;
            }
        }
        Point targetPosition = possibleRuns.get(randomDirection);
        while (maze.isPath(targetPosition, PATH_FORCE)) {
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
