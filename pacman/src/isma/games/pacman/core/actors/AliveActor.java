package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import isma.games.Direction;
import isma.games.GameObject;
import isma.games.Target;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.assets.TextureFactory;

public abstract class AliveActor extends Actor implements Target, GameObject {
    private static final int DEFAULT_WIDTH = 16;
    private static final int DEFAULT_HEIGHT = 16;
    protected Array<WorldEventListener> eventListeners = new Array<WorldEventListener>();
    protected final TextureFactory textureFactory;

    protected long frame;
    protected final ArrayMap<Direction, Animation> animations;

    private final String id;
    private final Rectangle center;
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
        center = new Rectangle(getX() + DEFAULT_WIDTH / 4, getY() + DEFAULT_HEIGHT / 4, DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
    }


    protected abstract TextureRegion getTextureRegionToDraw();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!visible) {
            //TODO a gerer via la methode visible de base pour actor...
            return;
        }
        //TODO normalement on file le temps, pas la frame...
        frame += 1;
        TextureRegion texture = getTextureRegionToDraw();
        batch.draw(texture,
                getX() * Assets.configuration.getScaleRatio(),
                getY() * Assets.configuration.getScaleRatio());
    }

    public void addWorldEventListeners(WorldEventListener eventListener) {
        eventListeners.add(eventListener);
    }
    public Rectangle getCenter() {
        return center;
    }

    @Override
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        center.x = getX() + DEFAULT_WIDTH / 4;
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        center.y = getY() + DEFAULT_HEIGHT / 4;
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

    public void die() {
        for (WorldEventListener eventListener : eventListeners) {
            eventListener.onConsumed(this);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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
}
