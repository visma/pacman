package isma.games.pacman.core.actors;

import java.util.List;

import isma.games.pacman.core.assets.Assets;

import static java.util.Arrays.asList;

public class ActorFactory {

    private ActorFactory() {
    }

    public static Pacman buildPacman() {
        Pacman pacman = new Pacman(Assets.configuration.getPacmanSpeed());
        ActorBuilder.reset(pacman);
        return pacman;
    }

    public static Ghost buildBlinky() {
        return buildGhost(ActorConstants.BLINKY.id);
    }

    public static Ghost buildPinky() {
        return buildGhost(ActorConstants.PINKY.id);
    }

    public static Ghost buildInky() {
        return buildGhost(ActorConstants.INKY.id);
    }

    public static Ghost buildClyde() {
        return buildGhost(ActorConstants.CLYDE.id);
    }

    public static List<Ghost> buildAllGhosts() {
        return asList(buildBlinky(), buildInky(), buildPinky(), buildClyde());
    }

    private static Ghost buildGhost(String id) {
        Ghost g = new Ghost(id, Assets.configuration.getGhostSpeed(id));
        ActorBuilder.reset(g);
        return g;
    }


}
