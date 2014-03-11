package isma.games;

import org.junit.Assert;
import org.junit.Test;

import static isma.games.Direction.*;
import static org.junit.Assert.assertEquals;

public class DirectionTest {
    @Test
    public void nextPosition() throws Exception {
        assertEquals(new Point(4, 2), NORTH.nextPosition(new Point(4, 1)));
        assertEquals(new Point(4, 0), SOUTH.nextPosition(new Point(4, 1)));
        assertEquals(new Point(5, 1), EAST.nextPosition(new Point(4, 1)));
        assertEquals(new Point(3, 1), WEST.nextPosition(new Point(4, 1)));
    }

    @Test
    public void previousPosition() throws Exception {
        assertEquals(new Point(4, 0), NORTH.previousPosition(new Point(4, 1)));
        assertEquals(new Point(4, 2), SOUTH.previousPosition(new Point(4, 1)));
        assertEquals(new Point(3, 1), EAST.previousPosition(new Point(4, 1)));
        assertEquals(new Point(5, 1), WEST.previousPosition(new Point(4, 1)));
    }

}
