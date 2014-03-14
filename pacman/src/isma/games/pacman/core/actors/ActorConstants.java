package isma.games.pacman.core.actors;

public enum ActorConstants {
    PACMAN("pacman", 104, 68),
    BLINKY("blinky", 104, 164),
    INKY("inky", 92, 140),
    PINKY("pinky", 104, 140),
    CLYDE("clyde", 116, 140);

    public final String id;
    final float originalX;
    final float originalY;

    ActorConstants(String id, float originalX, float originalY) {
        this.id = id;
        this.originalX = originalX;
        this.originalY = originalY;
    }

    public static ActorConstants get(AliveActor actor) {
        for (ActorConstants constants : values()) {
            if (constants.id.equals(actor.getId())) {
                return constants;
            }
        }
        throw new RuntimeException("actor id not registered");
    }
}
