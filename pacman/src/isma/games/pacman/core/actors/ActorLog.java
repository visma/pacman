package isma.games.pacman.core.actors;

public class ActorLog {
    public static void logIfBlinky(AliveActor actor, String message, Object... params) {
        if (actor.getId().equals("blinky")) {
            System.out.printf(message + "\n", params);
        }
    }
    public static void logIfPinky(AliveActor actor, String message, Object... params) {
        if (actor.getId().equals("pinky")) {
            System.out.printf(message + "\n", params);
        }
    }
}
