package isma.games;

import com.badlogic.gdx.math.Rectangle;

import static org.junit.Assert.assertEquals;

public abstract class AbstractCoreTest {
    protected void assertDoubleEquals(double expected, double actual) {
        assertEquals(expected, actual, 0.00001);
    }

    protected void assertRectangle(Rectangle expected, Rectangle actual) {
        assertDoubleEquals(expected.x, actual.x);
        assertDoubleEquals(expected.y, actual.y);
        assertDoubleEquals(expected.width, actual.width);
        assertDoubleEquals(expected.height, actual.height);
    }

}
