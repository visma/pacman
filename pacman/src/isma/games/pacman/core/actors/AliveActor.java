package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Map;

import isma.games.Direction;
import isma.games.GameObject;
import isma.games.Target;
import isma.games.pacman.core.assets.TextureFactory;

public abstract class AliveActor extends Actor implements Target, GameObject {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    protected Array<WorldEventListener> eventListeners = new Array<WorldEventListener>();

    protected final TextureFactory textureFactory;

    private final String id;
    protected long frame;
    protected final ArrayMap<Direction, Animation> animations;

    protected Direction currentDirection;
    protected boolean stopped;
    private float speed;
    protected boolean visible;


    protected AliveActor(String id, float speed) {
        this.speed = speed;
        this.id = id;
        frame = 0;
        textureFactory = new TextureFactory();
        animations = textureFactory.buildAliveActorDefautAnimations(getId());
        visible = true;
    }


    protected abstract TextureRegion getTextureRegionToDraw();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!visible) {
            return;
        }
        //TODO normalement on file le temps, pas la frame...
        frame += 1;
        TextureRegion texture = getTextureRegionToDraw();
        batch.draw(texture, getX(), getY());
    }

    public void addWorldEventListeners(WorldEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public Rectangle getCenter() {
        //TODO pas mettre en dur width et height
        int width = WIDTH;
        int height = HEIGHT;
        return new Rectangle(getX() + 4, getY() + 4, width, height);
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public String getId() {
        return id;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AliveActor)) return false;
        AliveActor that = (AliveActor) o;
        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void die() {
        for (WorldEventListener eventListener : eventListeners) {
            eventListener.onConsumed(this);
        }
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
