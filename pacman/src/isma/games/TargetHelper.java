package isma.games;

import com.badlogic.gdx.math.Vector2;

public class TargetHelper {
    private TargetHelper() {
    }

    public static boolean hit(Point a, Point b) {
        return a.equals(b);
    }

    public static boolean hit(Target a, Target b) {
        return a.getCenter().overlaps(b.getCenter());
    }

    //TODO TU
    public static float dst(Target a, Target b) {
//        Vector2 v1 = new Vector2(a.getCenter().x, a.getCenter().y);
//        Vector2 v2 = new Vector2(b.getCenter().x, b.getCenter().y);
//        return v1.dst(v2);
        final float x_d = b.getCenter().x - a.getCenter().x;
        final float y_d = b.getCenter().y - a.getCenter().y;
        return (float) Math.sqrt(x_d * x_d + y_d * y_d);
    }
}
