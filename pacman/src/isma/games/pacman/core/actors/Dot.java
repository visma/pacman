package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import isma.games.pacman.core.assets.TextureFactory;

public class Dot extends Food {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

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
            batch.draw(animation.getKeyFrame(frame++, true), getX(), getY());
        }
    }

    public Rectangle getCenter() {
        int width = WIDTH;
        int height = HEIGHT;
        return new Rectangle(getX(), getY(), width, height);
    }


    public boolean isEnergizer() {
        return energizer;
    }

    @Override
    public String toString() {
        return "x=" + getX() + ", y=" + getY() + ", energizer=" + energizer + ", alive=" + alive;
    }
}
