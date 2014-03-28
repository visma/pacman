package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.assets.TextureFactory;

public class Dot extends Food {
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 8;

    private final Animation animation;
    private boolean energizer;
    private int frame;
    private final Rectangle center;

    public Dot(boolean energizer, float x, float y) {
        this.energizer = energizer;
        alive = true;
        frame = 0;
        animation = new TextureFactory().buildDotAnimation(energizer);
        setX(x);
        setY(y);
        center = new Rectangle(getX(), getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (alive) {
            batch.draw(animation.getKeyFrame(frame++, true),
                    getX() * Assets.configuration.getScaleRatio(),
                    getY() * Assets.configuration.getScaleRatio());
        }
    }

    public Rectangle getCenter() {
        return center;
    }


    public boolean isEnergizer() {
        return energizer;
    }

    @Override
    public void setX(float x) {
        if (getX() != 0) {
            throw new RuntimeException("dot position is immutable");
        }
        super.setX(x);

    }

    @Override
    public void setY(float y) {
        if (getY() != 0) {
            throw new RuntimeException("dot position is immutable");
        }
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        throw new RuntimeException("dot position is immutable");
    }

    @Override
    public String toString() {
        return "x=" + getX() + ", y=" + getY() + ", energizer=" + energizer + ", alive=" + alive;
    }
}
