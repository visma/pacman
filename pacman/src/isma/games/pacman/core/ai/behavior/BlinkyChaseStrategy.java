package isma.games.pacman.core.ai.behavior;

import isma.games.Target;
import isma.games.pacman.core.manager.WorldContainer;

public class BlinkyChaseStrategy implements ChaseStrategy {
    @Override
    public Target searchTarget(WorldContainer world) {
        return world.getPacman();
    }
}
