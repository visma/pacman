package isma.games;

import java.math.BigDecimal;

public class NumberHelper {
    private NumberHelper() {
    }

    public static float scale(float f, int scale) {
        return new BigDecimal(f).setScale(scale, BigDecimal.ROUND_DOWN).floatValue();
    }

    public static int roundToNearestInteger(float f) {
        return new BigDecimal(f).setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue();
    }

    public static boolean isInteger(float f) {
        float integer = (int) f;
        return f == integer;
    }

    public static float roundToNearestInteger(float f, float minDelta) {
        int nearestInt = roundToNearestInteger(f);
        if (f == nearestInt){
            return f;
        }
        float delta = Math.abs(nearestInt - f);
        if (delta < minDelta) {
            return nearestInt;
        }
        return f;
    }
}
