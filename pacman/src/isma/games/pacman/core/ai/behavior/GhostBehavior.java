package isma.games.pacman.core.ai.behavior;

import isma.games.Target;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;

import static isma.games.Log.*;

public abstract class GhostBehavior {
    private final long duration;
    private long elapsed = 0;
    private long lastTime = 0;

    protected Ghost ghost;

    protected GhostBehavior(Ghost ghost, long duration) {
        this.ghost = ghost;
        this.duration = duration;
    }

    public boolean isOver(WorldContainer stage) {
        return elapsed > duration;
    }

    public abstract Target searchTarget(WorldContainer stage);

    public abstract GhostBehavior nextBehavior(WorldContainer world);

    public void update(WorldContainer world) {
        long time = System.currentTimeMillis();
        if (lastTime == 0) {
            lastTime = time;
        } else {
            elapsed += time - lastTime;
        }
        lastTime = time;
        //debug("%s elapsed behavior (%s) time : %s", ghost.getId(), getClass(), elapsed);
    }

}
