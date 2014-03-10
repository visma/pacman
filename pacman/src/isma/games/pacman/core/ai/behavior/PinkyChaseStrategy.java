package isma.games.pacman.core.ai.behavior;

import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

public class PinkyChaseStrategy implements ChaseStrategy {
    private static final int PACMAN_TILE_OFFSET = 5;

    @Override
    public Target searchTarget(WorldContainer world) {
        Maze maze = world.getMaze();
        Pacman pacman = world.getPacman();


        Point targetPosition = AIStrategyHelper.nextTile(maze, pacman, PACMAN_TILE_OFFSET);
        return TiledMapHelper.getTarget(maze, targetPosition);
    }


}
