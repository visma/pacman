package isma.games.utils;

import isma.games.Target;

public class TargetUtils {

    private TargetUtils() {
    }

    public static String stringify(Target t) {
        return "{x=" + t.getCenter().x + ", y=" + t.getCenter().y + "}";
    }
}
