package isma.games.math;

public class TrigoFunctionFactory {
    private TrigoFunctionFactory() {
    }

    public static SinusVariation buildSinus(final float xIncrement,
                                            final float yOffSet,
                                            final boolean abs,
                                            final float ratio) {
        return buildSinus(0, xIncrement, yOffSet, abs, ratio);
    }

    private static SinusVariation buildSinus(final float xOffSet,
                                             final float xIncrement,
                                             final float yOffSet,
                                             final boolean abs,
                                             final float ratio) {
        return new SinusVariation(xOffSet, xIncrement) {
            @Override
            public double nextValue() {
                double res = super.nextValue();
                if (abs) {
                    res = Math.abs(res);
                }
                return (res * ratio) + yOffSet;
            }
        };
    }
}
