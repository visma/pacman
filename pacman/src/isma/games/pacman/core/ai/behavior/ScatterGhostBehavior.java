package isma.games.pacman.core.ai.behavior;

import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.pacman.core.actors.Ghost.GhostState.NORMAL;

public class ScatterGhostBehavior extends GhostBehavior {
    private final static int TOTAL_CORNERS = 4;
    //current corners possible values {1, 2, 3, 4}
    private int currentCorner = 1;

    public ScatterGhostBehavior(Ghost ghost) {
        super(ghost, Assets.configuration.getScatterBehaviorDuration());
    }

    @Override
    public boolean isOver(WorldContainer world) {
        return ghost.getState() != NORMAL || super.isOver(world);
    }

    public Target searchTarget(WorldContainer world) {
        Maze maze = world.getMaze();
        Point ghostPosition = getGridPosition(maze, ghost);
        Point cornerPosition = maze.getGhostCorner(ghost.getId(), currentCorner);

        if (ghostPosition.dst(cornerPosition) <= 1) {
            currentCorner++;
            if (currentCorner == TOTAL_CORNERS + 1) {
                currentCorner = 1;
            }
            cornerPosition = maze.getGhostCorner(ghost.getId(), currentCorner);
        }
        return TiledMapHelper.getTarget(maze, cornerPosition);
    }

    @Override
    public GhostBehavior nextBehavior(WorldContainer world) {
        switch (ghost.getState()) {
            case NORMAL:
                return new ChaseGhostBehavior(ghost, ChaseStrategyFactory.build(ghost, world));
            case FRIGTHENED:
                return new FearBehavior(ghost, this);
            case NAKED:
                return new NakedGhostBehavior(ghost);
        }
        throw new RuntimeException("unreachable statement");
    }


}
