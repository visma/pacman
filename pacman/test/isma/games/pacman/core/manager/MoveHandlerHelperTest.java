package isma.games.pacman.core.manager;

import com.badlogic.gdx.math.Rectangle;

import junit.framework.Assert;

import org.junit.Test;

import isma.games.AbstractCoreTest;
import isma.games.Direction;
import isma.games.SimpleTarget;
import isma.games.Target;
import isma.games.TiledMapWrapper;
import isma.games.pacman.core.actors.AliveActor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveHandlerHelperTest extends AbstractCoreTest {
    @Test
    public void nextCenterPosition_outOfBounds() throws Exception {
        AliveActor actor = mock(AliveActor.class);
        when(actor.getCenter()).thenReturn(new Rectangle(0, 0, 8, 8));
        when(actor.getCurrentDirection()).thenReturn(Direction.WEST);

        TiledMapWrapper map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);
        when(map.getBounds()).thenReturn(new Rectangle(0, 0, 3, 2));

        Target target = MoveHandlerHelper.nextCenterPosition(map, actor, 0.5f);
        assertRectangle(new Rectangle(23.5f, 0, 8, 8), target.getCenter());
    }

    @Test
    public void crossTile_horizontal_true() throws Exception{
        assertCrossTile(7.99f, 0, 8f, 0, true);
    }
    @Test
    public void crossTile_horizontal_false() throws Exception{
        assertCrossTile(7.98f, 0, 7.99f, 0, false);
    }

    @Test
    public void crossTile_vertical_true() throws Exception{
        assertCrossTile(18, 7.99f, 18, 8, true);
    }

    @Test
    public void crossTile_vertical_false() throws Exception{
        assertCrossTile(18, 7.98f, 18, 7.99f, false);
    }


    private void assertCrossTile(float currX, float currY, float nextX, float nextY, boolean expected) {
        TiledMapWrapper map = mock(TiledMapWrapper.class);
        when(map.getTileWidth()).thenReturn(8);
        when(map.getTileHeight()).thenReturn(8);

        Target curr = new SimpleTarget(currX, currY, 1,1);
        Target next = new SimpleTarget(nextX, nextY, 1,1);

        Assert.assertEquals(expected, MoveHandlerHelper.crossTile(map, curr, next));
        Assert.assertEquals(expected, MoveHandlerHelper.crossTile(map, next, curr));
    }

}
