package isma.games.pacman.core.ai.behavior;

import isma.games.Target;
import isma.games.pacman.core.manager.WorldContainer;

public interface ChaseStrategy {
    Target searchTarget(WorldContainer world);
}
