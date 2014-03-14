package isma.games.pacman.core.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import isma.games.Point;
import isma.games.PointCache;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.tiled.Maze;
import isma.games.test.ActorMockFactory;
import isma.games.test.MazeMockFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ActorStateManagerTest {
    private static final Point FOOD_GRID_POSITION = PointCache.get(1, 1);
    private ActorStateManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new ActorStateManager();
    }

    @Test
    public void consumeFood_not() throws Exception {
        WorldContainer stage = buildStage(new Vector2(0, 8));
        Point pacmanGridPosition = TiledMapHelper.getGridPosition(stage.getMaze(), stage.getPacman());
        assertEquals(FOOD_GRID_POSITION.onLeft(), pacmanGridPosition);

        manager.handleState(stage);

        verify(stage.getDotAt(FOOD_GRID_POSITION), times(0)).die();
    }

    @Test
    public void consumeFood_caseCorner_before() throws Exception {
        WorldContainer stage = buildStage(new Vector2(7, 8));
        Point pacmanGridPosition = TiledMapHelper.getGridPosition(stage.getMaze(), stage.getPacman());
        assertEquals(FOOD_GRID_POSITION.onLeft(), pacmanGridPosition);

        manager.handleState(stage);

        verify(stage.getDotAt(FOOD_GRID_POSITION), times(1)).die();
    }


    @Test
    public void consumeFood_caseCorner_at() throws Exception {
        WorldContainer stage = buildStage(new Vector2(8, 8));
        Point pacmanGridPosition = TiledMapHelper.getGridPosition(stage.getMaze(), stage.getPacman());
        assertEquals(FOOD_GRID_POSITION, pacmanGridPosition);

        manager.handleState(stage);

        verify(stage.getDotAt(FOOD_GRID_POSITION), times(1)).die();
    }


    @Test
    public void consumeFood_caseCorner_after() throws Exception {
        WorldContainer stage = buildStage(new Vector2(8, 7));

        manager.handleState(stage);

        verify(stage.getDotAt(FOOD_GRID_POSITION), times(1)).die();
    }

    private WorldContainer buildStage(Vector2 pacmanCenterPosition) {
        WorldContainer stage = mock(WorldContainer.class);
        Maze maze = MazeMockFactory.mock(PointCache.get(2, 2));

        Pacman pacman = ActorMockFactory.mockPacman(pacmanCenterPosition);
        when(stage.getPacman()).thenReturn(pacman);

        Dot cornerFood = ActorMockFactory.mockDot(new Vector2(8, 8), true, false);
        when(stage.getDotAt(FOOD_GRID_POSITION)).thenReturn(cornerFood);
        when(stage.getRemainingDots()).thenReturn(new Array<Dot>(new Dot[]{cornerFood}));
        when(stage.getMaze()).thenReturn(maze);
        return stage;
    }
}
