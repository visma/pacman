package isma.games.pacman.core.manager;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import isma.games.Point;
import isma.games.PointCache;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;
import isma.games.test.MazeMockFactory;
import org.junit.Test;

import static isma.games.Direction.WEST;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultMoveHandlerTest {

    @Test
    public void canMoveOutOfBounds_path() throws Exception {
        DefaultMoveHandler moveHandler = new DefaultMoveHandler();
        Maze maze = mockMaze(PointCache.get(0, 0), PointCache.get(2, 0));
        AliveActor actor = mockActor(0, 0, 8);

        assertTrue(moveHandler.canMove(actor, maze, WEST, 2));
    }

    @Test
    public void cantMoveOutOfBounds_noPath() throws Exception {
        DefaultMoveHandler moveHandler = new DefaultMoveHandler();
        Maze maze = mockMaze(PointCache.get(0, 0));
        AliveActor actor = mockActor(0, 0, 8);

        assertFalse(moveHandler.canMove(actor, maze, WEST, 2));
    }


    private Maze mockMaze(Point... pathPoints) {
        TiledMapTileLayer.Cell cell = mockCell(true);
        boolean path = cell.getTile().getProperties().get("path").equals("1");

        Maze maze = MazeMockFactory.mock(PointCache.get(3, 3));
        for (Point pathPoint : pathPoints) {
            when(maze.getCell(0, pathPoint)).thenReturn(cell);
        }
        when(maze.isPath(cell, 1)).thenReturn(path);
        return maze;
    }

    private TiledMapTileLayer.Cell mockCell(boolean path) {
        MapProperties mapProperties = mock(MapProperties.class);
        when(mapProperties.get("path")).thenReturn(path ? "1" : "");

        TiledMapTile tile = mock(TiledMapTile.class);
        when(tile.getProperties()).thenReturn(mapProperties);

        TiledMapTileLayer.Cell cell = mock(TiledMapTileLayer.Cell.class);
        when(cell.getTile()).thenReturn(tile);
        return cell;
    }


    private AliveActor mockActor(float x, float y, float size) {
        AliveActor actor = mock(AliveActor.class);
        when(actor.getCenter()).thenReturn(new Rectangle(x, y, size, size));
        return actor;
    }
}
