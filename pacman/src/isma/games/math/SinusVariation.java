package isma.games.math;

public class SinusVariation {
    private final float xIncrement;

    private float x = 0;

    SinusVariation(float xOffset, float xIncrement) {
        this.xIncrement = xIncrement;
        this.x = xOffset;
    }

    public double nextValue() {
        double res = Math.sin(x);
        x += xIncrement;
        return res;
    }

}

