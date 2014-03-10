package isma.games.pacman.core.ai.behavior;

import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeMoveHelper;

public class InkyChaseStrategy implements ChaseStrategy {
    private Pacman pacman;
    private Ghost blinky;

    public InkyChaseStrategy(Pacman pacman, Ghost blinky) {
        this.pacman = pacman;
        this.blinky = blinky;
    }

    @Override
    public Target searchTarget(WorldContainer world) {

        Maze maze = world.getMaze();
        Point pacmanNextTwoTilePosition = AIStrategyHelper.nextTile(maze, pacman, 2);
        Point blinkyPosition = TiledMapHelper.getGridPosition(maze, blinky);

        Point vector = blinkyPosition.getVector(pacmanNextTwoTilePosition);
        Point idealTarget = blinkyPosition.add(vector).add(vector);

        Point targetPosition = MazeMoveHelper.getClosestPathOf(maze, idealTarget, 2, 5);

        return TiledMapHelper.getTarget(maze, targetPosition);
    }
}
