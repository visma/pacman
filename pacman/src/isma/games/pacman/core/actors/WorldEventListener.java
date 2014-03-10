package isma.games.pacman.core.actors;

public interface WorldEventListener {
    public void onConsumed(Food dot);

    public void onConsumed(AliveActor actor);
}
