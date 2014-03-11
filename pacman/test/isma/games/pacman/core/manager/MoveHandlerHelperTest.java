package isma.games.pacman.core.manager;

import com.badlogic.gdx.math.Rectangle;
import isma.games.AbstractCoreTest;
import isma.games.Direction;
import isma.games.Target;
import isma.games.TiledMapWrapper;
import isma.games.pacman.core.actors.AliveActor;
import org.junit.Test;

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
}
