package isma.games;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberHelperTest {

    @Test
    public void roundToNearestInteger() throws Exception {
        assertEquals(8, NumberHelper.roundToNearestInteger(7.9f));
        assertEquals(8, NumberHelper.roundToNearestInteger(7.99f));
        assertEquals(7, NumberHelper.roundToNearestInteger(7.1f));
        assertEquals(7, NumberHelper.roundToNearestInteger(7.01f));
    }

    @Test
    public void isInteger() throws Exception {
        assertTrue(NumberHelper.isInteger(-1));
        assertTrue(NumberHelper.isInteger(0));
        assertTrue(NumberHelper.isInteger(1));

        assertFalse(NumberHelper.isInteger(0.1f));
        assertFalse(NumberHelper.isInteger(0.01f));
        assertFalse(NumberHelper.isInteger(0.001f));
    }

    @Test
    public void scale(){
        float f = 7.9999995f;
        assertDoubleEquals(8f, NumberHelper.roundToNearestInteger(f, 0.000001f));
        assertDoubleEquals(f,  NumberHelper.roundToNearestInteger(f, 0.0000001f));
    }

    private void assertDoubleEquals(double expected, double actual) {
        assertEquals(expected, actual, 0.000000001f);
    }
}
