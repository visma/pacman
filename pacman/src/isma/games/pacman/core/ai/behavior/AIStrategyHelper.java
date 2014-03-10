package isma.games.pacman.core.ai.behavior;

import isma.games.Direction;
import isma.games.Point;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Direction.*;

class AIStrategyHelper {
    private AIStrategyHelper() {
    }

    static Point nextTile(Maze maze, AliveActor actor, int tileOffset) {
        Direction direction = actor.getCurrentDirection();
        Point targetPosition = TiledMapHelper.getGridPosition(maze, actor);

        for (int i = 0; i < tileOffset; i++) {
            if (maze.isPath(targetPosition.onNext(direction))) {
                targetPosition = targetPosition.onNext(direction);
            } else {
                if (direction.isHorizontal()) {
                    if (maze.isPath(targetPosition.onNext(NORTH))) {
                        direction = NORTH;
                        targetPosition = targetPosition.onNext(NORTH);
                    } else {
                        direction = SOUTH;
                        targetPosition = targetPosition.onNext(SOUTH);
                    }
                } else {
                    if (maze.isPath(targetPosition.onNext(WEST))) {
                        direction = WEST;
                        targetPosition = targetPosition.onNext(WEST);
                    } else {
                        direction = EAST;
                        targetPosition = targetPosition.onNext(EAST);
                    }
                }
            }
        }
        return targetPosition;
    }
}
