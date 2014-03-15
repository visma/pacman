package isma.games.pacman.core.ai.behavior;

import com.badlogic.gdx.utils.ArrayMap;

import java.util.Random;

import isma.games.Direction;
import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.TiledMapHelper.handleOutOfBounds;
import static isma.games.pacman.core.actors.Ghost.GhostState.FRIGTHENED;

public class FearBehavior extends GhostBehavior {
    private GhostBehavior precBehavior;

    public FearBehavior(Ghost ghost, GhostBehavior precBehavior) {
        super(ghost, Assets.configuration.getFearBehaviorDuration());
        this.precBehavior = precBehavior;
        ghost.setFrightened(true);
    }

    @Override
    public boolean isOver(WorldContainer world) {
        return ghost.getState() != FRIGTHENED || super.isOver(world);
    }

    @Override
    public GhostBehavior nextBehavior(WorldContainer world) {
        precBehavior.lastTime = System.currentTimeMillis();
        return precBehavior;
    }

    public void update(WorldContainer world) {
        super.update(world);
        if (isOver(world)) {
            if (ghost.getState() == FRIGTHENED) {
                ghost.setFrightened(false);
            }
        }
    }

    public Target searchTarget(WorldContainer world) {
        Maze maze = world.getMaze();
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



}
