package isma.games.pacman.core.ai.behavior;

import isma.games.pacman.core.actors.ActorConstants;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;

public class ChaseStrategyFactory {
    private ChaseStrategyFactory() {
    }

    public static ChaseStrategy build(Ghost ghost, WorldContainer world) {
        if (ghost.getId().equals(ActorConstants.BLINKY.id)) {
            return new BlinkyChaseStrategy();
        } else if (ghost.getId().equals(ActorConstants.INKY.id)) {
            return new InkyChaseStrategy(world.getPacman(), world.getGhost(ActorConstants.BLINKY.id));
        } else if (ghost.getId().equals(ActorConstants.PINKY.id)) {
            return new PinkyChaseStrategy();
        } else {
            return new ClydeChaseStrategy(ghost);
        }
    }
}
