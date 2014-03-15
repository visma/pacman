package isma.games.pacman.core.ai;

import isma.games.Target;
import isma.games.pacman.core.GraphBuilder;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.ai.behavior.ChaseGhostBehavior;
import isma.games.pacman.core.ai.behavior.ChaseStrategyFactory;
import isma.games.pacman.core.ai.behavior.GhostBehavior;
import isma.games.pacman.core.manager.WorldContainer;

public class GhostAIMoveHandler extends AIMoveHandler {
    private GhostBehavior behavior;

    public GhostAIMoveHandler(Ghost actor, WorldContainer world) {
        super(actor, world, new GraphBuilder());
        behavior = new ChaseGhostBehavior(actor, ChaseStrategyFactory.build(actor, world));
    }

    @Override
    protected boolean nextMoveChangeTurnTile(float remainingLen) {
        behavior.update(world);
        if (behavior.isOver(world)) {
            behavior = behavior.nextBehavior(world);
        }
        return super.nextMoveChangeTurnTile(remainingLen);
    }

    @Override
    protected Target searchTarget() {
        return behavior.searchTarget(world);
    }

    @Override
    public int getPathForce() {
        return 2;
    }


}
