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

    public Dot(boolean isEnergizer) {
        energizer = isEnergizer;
        alive = true;
        frame = 0;
        animation = new TextureFactory().buildDotAnimation(isEnergizer);
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
        return new Rectangle(getX(), getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    public boolean isEnergizer() {
        return energizer;
    }

    @Override
    public String toString() {
        return "x=" + getX() + ", y=" + getY() + ", energizer=" + energizer + ", alive=" + alive;
    }
}
