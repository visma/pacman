package isma.games;

import com.badlogic.gdx.math.Rectangle;

public class SimpleTarget implements Target {
    private final Rectangle center;

    public SimpleTarget(float x, float y, float width, float height) {
        this.center = new Rectangle(x, y, width, height);
    }

    public SimpleTarget(Rectangle center) {
        this.center = new Rectangle(center);
    }

    @Override
    public Rectangle getCenter() {
        return center;
    }


    public float getX() {
        return center.x;
    }

    public float getY() {
        return center.y;
    }

    public void setX(float x) {
        center.x = x;
    }

    public void setY(float y) {
        center.y = y;
    }
}
