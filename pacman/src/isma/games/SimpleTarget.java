package isma.games;

import com.badlogic.gdx.math.Rectangle;

public class SimpleTarget implements Target {
    public float x;
    public float y;
    private float width;
    private float height;

    public SimpleTarget(float x, float y, float dimension) {
        this(new Rectangle(x, y, dimension, dimension));
    }

    public SimpleTarget(float x, float y, float width, float height) {
        this(new Rectangle(x, y, width, height));
    }

    public SimpleTarget(Rectangle center) {
        this.x = center.x;
        this.y = center.y;
        this.width = center.width;
        this.height = center.height;
    }

    @Override
    public Rectangle getCenter() {
        return new Rectangle(x, y, width, height);
    }


}
