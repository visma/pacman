package isma.games.pacman.core.ai.behavior;

import isma.games.Direction;
import isma.games.Point;
import isma.games.Target;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;
import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.TiledMapHelper.getTarget;
import static isma.games.pacman.core.tiled.MazeMoveHelper.nextTurnTileOn;

public class ClydeChaseStrategy implements ChaseStrategy {
    private static final float AWARE_TILE_RADIUS = 8;
    private Ghost clyde;


    public ClydeChaseStrategy(Ghost clyde) {
        this.clyde = clyde;
    }

    @Override
    public Target searchTarget(WorldContainer world) {
        Maze maze = world.getMaze();
        Pacman pacman = world.getPacman();

        Point pacmanPosition = getGridPosition(maze, pacman);
        Point clydePosition = getGridPosition(maze, clyde);

        if (pacmanPosition.dst(clydePosition) < AWARE_TILE_RADIUS) {
            return pacman;
        }
        Target target = nextHorizontalTurnTile(maze, clyde);
        return target;
    }

    private Target nextHorizontalTurnTile(Maze maze, Ghost clyde) {
        Point clydePosition = getGridPosition(maze, clyde);
        Direction direction = null;
        switch (clyde.getCurrentDirection()) {
            case EAST:
                direction = chooseDirection(maze, clyde, SOUTH, NORTH);
                break;
            case SOUTH:
                direction = chooseDirection(maze, clyde, WEST, EAST);
                break;
            case WEST:
                direction = chooseDirection(maze, clyde, NORTH, SOUTH);
                break;
            case NORTH:
                direction = chooseDirection(maze, clyde, EAST, WEST);
                break;
        }
        Point nextTurnTile = nextTurnTileOn(maze, clydePosition, direction, direction.nextOnClockwise(), 2);
        return getTarget(maze, nextTurnTile);
    }

    private Direction chooseDirection(Maze maze,
                                      Ghost clyde,
                                      Direction rightHandDirection,
                                      Direction leftHandDirection) {
        Point clydePosition = getGridPosition(maze, clyde);
        if (maze.isPath(clydePosition.onNext(rightHandDirection), 2)) {
            return rightHandDirection;
        } else if (maze.isPath(clydePosition.onNext(leftHandDirection), 2)) {
            return leftHandDirection;
        } else {
            //Inside ghost house
            return clyde.getCurrentDirection();
        }
    }
}
