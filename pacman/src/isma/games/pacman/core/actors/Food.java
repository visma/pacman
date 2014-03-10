package isma.games.pacman.core.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import isma.games.Target;

import java.util.ArrayList;
import java.util.List;

public abstract class Food extends Actor implements Target {
    protected boolean alive;
    private List<WorldEventListener> eventListeners = new ArrayList<WorldEventListener>();

    public void die() {
        alive = false;
        for (WorldEventListener eventListener : eventListeners) {
            eventListener.onConsumed(this);
        }
    }


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void addWorldEventListeners(WorldEventListener eventListener) {
        eventListeners.add(eventListener);
    }
}
