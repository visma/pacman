package isma.games;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import isma.games.test.TargetMockFactory;
import org.junit.Test;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.test.TargetMockFactory.mockTarget;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TiledMapHelperTest extends AbstractCoreTest {

    @Test
    public void gridPosition_position() throws Exception {
        TiledMapWrapper map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);

        assertEquals(new Point(0, 0), getGridPosition(map, new Vector2(0, 0)));
        assertEquals(new Point(0, 0), getGridPosition(map, new Vector2(7, 7)));
        assertEquals(new Point(0, 1), getGridPosition(map, new Vector2(7, 8)));
        assertEquals(new Point(1, 1), getGridPosition(map, new Vector2(8, 8)));

        assertEquals(new Point(-1, -1), getGridPosition(map, new Vector2(-1, -1)));
        assertEquals(new Point(-2, -2), getGridPosition(map, new Vector2(-8, -8)));
    }

    @Test
    public void gridPosition_target() throws Exception {
        TiledMapWrapper map = mock(TiledMapWrapper.class);
        int tileSize = 8;
        when(map.getTileWidth()).thenReturn(tileSize);
        when(map.getTileHeight()).thenReturn(tileSize);

        assertEquals(new Point(0, 0), getGridPosition(map, mockTarget(0, 0, tileSize)));
        assertEquals(new Point(0, 0), getGridPosition(map, mockTarget(7, 7, tileSize)));
        assertEquals(new Point(0, 1), getGridPosition(map, mockTarget(7, 8, tileSize)));
        assertEquals(new Point(1, 1), getGridPosition(map, mockTarget(8, 8, tileSize)));

        assertEquals(new Point(-2, -2), getGridPosition(map, mockTarget(-8, -8, tileSize)));
        assertEquals(new Point(-1, -1), getGridPosition(map, mockTarget(-1, -1, tileSize)));
    }

    @Test
    public void bounds() throws Exception {
        TiledMapWrapper map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);
        when(map.getBounds()).thenReturn(new Rectangle(0, 0, 3, 2));

        Rectangle rect = new Rectangle(-0.1f, -0.1f, 8, 8);
        TiledMapHelper.handleOutOfBounds(map, rect);
        assertDoubleEquals(23.9f, rect.x);
        assertDoubleEquals(15.9f, rect.y);

        rect = new Rectangle(24.1f, 16.1f, 8, 8);
        TiledMapHelper.handleOutOfBounds(map, rect);
        assertDoubleEquals(0.1f, rect.x);
        assertDoubleEquals(0.1f, rect.y);

        map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);
        when(map.getBounds()).thenReturn(new Rectangle(0, 0, 3, 2));
        rect = new Rectangle(24.70f, 16, 8, 8);
        TiledMapHelper.handleOutOfBounds(map, rect);
        assertDoubleEquals(0.70f, rect.x);
    }

    @Test
    public void bounds_gameObject() throws Exception {
        TiledMapWrapper map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);
        when(map.getBounds()).thenReturn(new Rectangle(0, 0, 3, 2));

        Rectangle rect = new Rectangle(24.70f, 16, 8, 8);
        GameObject gameObject = new InnerGameObject(rect);
        TiledMapHelper.handleOutOfBounds(map, gameObject);
        assertDoubleEquals(0.70f, gameObject.getCenter().x);
    }

    @Test
    public void bounds_gridPosition() throws Exception {
        TiledMapWrapper map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);
        when(map.getBounds()).thenReturn(new Rectangle(0, 0, 3, 2));

        Point point = new Point(3, 1);
        TiledMapHelper.handleOutOfBounds(map, point);
        assertEquals(new Point(0, 1), point);

        point = new Point(2, 2);
        TiledMapHelper.handleOutOfBounds(map, point);
        assertEquals(new Point(2, 0), point);
    }

    private class InnerGameObject implements GameObject {
        private Rectangle center;

        private InnerGameObject(Rectangle center) {
            this.center = center;
        }

        @Override
        public float getX() {
            return center.x - 4;
        }

        @Override
        public float getY() {
            return center.y - 4;
        }

        @Override
        public void setX(float x) {
            center.x = x + 4;
        }

        @Override
        public void setY(float y) {
            center.y = y + 4;
        }

        @Override
        public Rectangle getCenter() {
            return center;
        }
    }
}
