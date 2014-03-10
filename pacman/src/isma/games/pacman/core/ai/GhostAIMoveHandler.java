package isma.games.pacman.core.ai;

import isma.games.Target;
import isma.games.graph.GraphBuilder;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.ai.behavior.ChaseGhostBehavior;
import isma.games.pacman.core.ai.behavior.ChaseStrategyFactory;
import isma.games.pacman.core.ai.behavior.GhostBehavior;
import isma.games.pacman.core.ai.behavior.ScatterGhostBehavior;
import isma.games.pacman.core.manager.WorldContainer;

import static isma.games.Log.trace;
import static isma.games.Log.warn;

public class GhostAIMoveHandler extends AIMoveHandler {
    private GhostBehavior behavior;
    public GhostAIMoveHandler(Ghost actor, WorldContainer world) {
        super(actor, world, new GraphBuilder());
        behavior = new ChaseGhostBehavior(actor, ChaseStrategyFactory.build(actor, world));
        //behavior = new FearBehavior(actor);
    }

    @Override
    protected boolean nextMoveChangeTurnTile(float remainingLen) {
        behavior.update(world);
        if (behavior.isOver(world)){
            behavior = behavior.nextBehavior(world);
            //trace("%s : next behavior %s", aiActor.getId(), behavior.getClass());
        }
        if (behavior instanceof ScatterGhostBehavior){
            //IA must be used even if not on a turn tile for this behavior
            return true;
        }
        return super.nextMoveChangeTurnTile(remainingLen);
    }

    @Override
    protected Target searchTarget() {
        //warn("%s search target with %s", aiActor.getId(),  behavior.getClass());
        return behavior.searchTarget(world);
    }

    @Override
    public int getPathForce() {
        return 2;
    }


}
