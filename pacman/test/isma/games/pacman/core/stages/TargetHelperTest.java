package isma.games.pacman.core.stages;


import com.badlogic.gdx.math.Rectangle;
import isma.games.Target;
import isma.games.TargetHelper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TargetHelperTest {

    @Test
    public void hit() throws Exception {
        Target a = buildTarget(0, 0, 8, 8);
        Target b = buildTarget(7, 7, 8, 8);
        assertTrue(TargetHelper.hit(a, b));
        assertTrue(TargetHelper.hit(b, a));

        a = buildTarget(0, 0, 8, 8);
        b = buildTarget(8, 7, 8, 8);
        assertFalse(TargetHelper.hit(a, b));
        assertFalse(TargetHelper.hit(b, a));

        a = buildTarget(206.47997f, 248, 8, 8);
        b = buildTarget(208, 248, 8, 8);
        assertTrue(TargetHelper.hit(a, b));
        assertTrue(TargetHelper.hit(b, a));
    }

    private Target buildTarget(final float x, final float y, final float width, final float height) {
        return new Target() {
            @Override
            public Rectangle getCenter() {
                return new Rectangle(x, y, width, height);
            }
        };
    }
}
