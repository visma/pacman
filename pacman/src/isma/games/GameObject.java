package isma.games;

import com.badlogic.gdx.math.Rectangle;

public interface GameObject {
    float getX();

    float getY();

    void setX(float x);

    void setY(float y);

    Rectangle getCenter();
}
