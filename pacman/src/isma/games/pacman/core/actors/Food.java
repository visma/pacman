package isma.games.pacman.core.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import isma.games.Target;

public abstract class Food extends Actor implements Target {
    protected boolean alive;
    private Array<WorldEventListener> eventListeners = new Array<WorldEventListener>();

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
        setVisible(alive);
    }

    public void addWorldEventListeners(WorldEventListener eventListener) {
        eventListeners.add(eventListener);
    }
}
