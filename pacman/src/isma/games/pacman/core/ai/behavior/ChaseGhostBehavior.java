package isma.games.pacman.core.ai.behavior;

import isma.games.Target;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.manager.WorldContainer;

import static isma.games.pacman.core.actors.Ghost.GhostState.NORMAL;

public class ChaseGhostBehavior extends GhostBehavior {
    private ChaseStrategy strategy;

    public ChaseGhostBehavior(Ghost ghost, ChaseStrategy strategy) {
        super(ghost, Assets.configuration.getChaseBehaviorDuration());
        this.strategy = strategy;
    }


    @Override
    public boolean isOver(WorldContainer world) {
        return ghost.getState() != NORMAL || super.isOver(world);
    }

    @Override
    public GhostBehavior nextBehavior(WorldContainer world) {
        switch (ghost.getState()) {
            case NORMAL:
                return new ScatterGhostBehavior(ghost);
            case FRIGTHENED:
                return new FearBehavior(ghost, this);
            case NAKED:
                return new NakedGhostBehavior(ghost);
        }
        throw new RuntimeException("unreachable statement");
    }

    public Target searchTarget(WorldContainer world) {
        return strategy.searchTarget(world);
    }


}
