package isma.games.pacman.core.ai.behavior;

import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.pacman.core.actors.Ghost.GhostState.NAKED;

public class NakedGhostBehavior extends GhostBehavior {
    public NakedGhostBehavior(Ghost ghost) {
        super(ghost, 99999999);
    }

    @Override
    public boolean isOver(WorldContainer world) {
        return ghost.getState() != NAKED || super.isOver(world);

//        Maze maze = stage.getMaze();
//        Point ghostPosition = getGridPosition(maze, ghost);
//        Point ghostHouse = maze.getGhostHouse();
//        boolean hit = TargetHelper.hit(ghostHouse, ghostPosition);
//        if (hit) {
//            ghost.setState(NORMAL);
//        }
//        return hit;
    }

    public Target searchTarget(WorldContainer world) {
        Maze maze = world.getMaze();
        return TiledMapHelper.getTarget(maze, maze.getGhostHouse());
    }


    @Override
    public GhostBehavior nextBehavior(WorldContainer world) {
        return new ChaseGhostBehavior(ghost, ChaseStrategyFactory.build(ghost, world));
    }
}
