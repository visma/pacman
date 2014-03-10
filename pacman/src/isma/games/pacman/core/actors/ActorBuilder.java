package isma.games.pacman.core.actors;

import java.util.List;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.WEST;
import static isma.games.pacman.core.actors.Pacman.PacmanState.ALIVE;

public class ActorBuilder {
    private ActorBuilder() {
    }

    public static void resetAll(Pacman pacman, List<Ghost> ghosts){
        reset(pacman);
        for (Ghost ghost : ghosts) {
            reset(ghost);
        }
    }

    public static void reset(Ghost ghost) {
        ActorConstants constants = ActorConstants.get(ghost);
        ghost.setX(constants.originalX);
        ghost.setY(constants.originalY);
        ghost.revive();
        ghost.setCurrentDirection(WEST);
        ghost.setVisible(true);

    }

    public static void reset(Pacman pacman) {
        pacman.setX(ActorConstants.PACMAN.originalX);
        pacman.setY(ActorConstants.PACMAN.originalY);
        pacman.setState(ALIVE);
        pacman.setCurrentDirection(WEST);
        pacman.setVisible(true);
    }
}
