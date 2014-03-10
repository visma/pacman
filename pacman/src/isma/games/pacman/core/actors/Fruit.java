package isma.games.pacman.core.actors;

import com.badlogic.gdx.math.Rectangle;

public class Fruit extends Food {
    public Rectangle getCenter() {
        int width = 8;
        int height = 8;
        return new Rectangle(getX(), getY(), width, height);
    }
}
