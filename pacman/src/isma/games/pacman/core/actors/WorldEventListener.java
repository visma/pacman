package isma.games.pacman.core.actors;

import static isma.games.pacman.core.actors.Ghost.GhostState;

public interface WorldEventListener {
    public void onConsumed(Food food);

    public void onConsumed(AliveActor actor);

    public void onStateChanged(Ghost ghost, GhostState oldState, GhostState newState);
}
