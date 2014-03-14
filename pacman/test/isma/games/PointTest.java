package isma.games;

import org.junit.Test;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;
import static org.junit.Assert.assertEquals;

public class PointTest {
    @Test
    public void nextPosition() throws Exception {
        assertEquals(new Point(4, 2), new Point(4, 1).onNext(NORTH));
        assertEquals(new Point(4, 0), new Point(4, 1).onNext(SOUTH));
        assertEquals(new Point(5, 1), new Point(4, 1).onNext(EAST));
        assertEquals(new Point(3, 1), new Point(4, 1).onNext(WEST));
    }

    @Test
    public void previousPosition() throws Exception {
        assertEquals(new Point(4, 0), new Point(4, 1).onPrevious(NORTH));
        assertEquals(new Point(4, 2), new Point(4, 1).onPrevious(SOUTH));
        assertEquals(new Point(3, 1), new Point(4, 1).onPrevious(EAST));
        assertEquals(new Point(5, 1), new Point(4, 1).onPrevious(WEST));
    }

}
