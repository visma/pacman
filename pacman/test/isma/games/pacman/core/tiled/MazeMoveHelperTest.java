package isma.games.pacman.core.tiled;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import isma.games.AbstractCoreTest;
import isma.games.Point;
import isma.games.test.MazeMockFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static isma.games.Direction.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MazeMoveHelperTest extends AbstractCoreTest {
    /**
     * use tiled_overview.png to understand code
     */
    @Test
    public void scenario() throws Exception {
        Maze maze = mockMaze();

        assertFalse(maze.isPath(new Point(0, 1), 1));
        assertTrue(maze.isPath(new Point(1, 1), 1));
        assertTrue(maze.isPath(new Point(2, 1), 1));
        assertTrue(maze.isPath(new Point(3, 1), 1));
        assertTrue(maze.isPath(new Point(4, 1), 1));
        /***************************************************************
         * PATH LENGTH - X/Y INT/FLOAT
         ***************************************************************/
        assertDoubleEquals(24, MazeMoveHelper.getPathLength(maze, new Rectangle(8, 8, 8, 8), EAST, 1));
        assertDoubleEquals(22, MazeMoveHelper.getPathLength(maze, new Rectangle(10, 8, 8, 8), EAST, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(32, 8, 8, 8), EAST, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(999, 8, 8, 8), EAST, 1));
        assertDoubleEquals(23.5, MazeMoveHelper.getPathLength(maze, new Rectangle(8.5f, 8, 8, 8), EAST, 1));

        assertDoubleEquals(16, MazeMoveHelper.getPathLength(maze, new Rectangle(24, 8, 8, 8), WEST, 1));
        assertDoubleEquals(23.5, MazeMoveHelper.getPathLength(maze, new Rectangle(31.5f, 8, 8, 8), WEST, 1));

        assertDoubleEquals(8, MazeMoveHelper.getPathLength(maze, new Rectangle(24, 8, 8, 8), NORTH, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(18, 8, 8, 8), NORTH, 1));
        assertDoubleEquals(7.5, MazeMoveHelper.getPathLength(maze, new Rectangle(24, 8.5f, 8, 8), NORTH, 1));

        assertDoubleEquals(8, MazeMoveHelper.getPathLength(maze, new Rectangle(24, 16, 8, 8), SOUTH, 1));
        assertDoubleEquals(7.5, MazeMoveHelper.getPathLength(maze, new Rectangle(24, 15.5f, 8, 8), SOUTH, 1));

        /*************************************************************
         * PATH LENGTH - BOUNDS TEST
         *************************************************************/
        assertDoubleEquals(24, MazeMoveHelper.getPathLength(maze, new Rectangle(32, 8, 8, 8), WEST, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(32, 7.99f, 8, 8), WEST, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(32, 8.01f, 8, 8), WEST, 1));

        assertDoubleEquals(8, MazeMoveHelper.getPathLength(maze, new Rectangle(24, 8, 8, 8), NORTH, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(23, 8, 8, 8), NORTH, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLength(maze, new Rectangle(25, 8, 8, 8), NORTH, 1));


        /***************************************************************
         * PATH LENGTH BEFORE DIRECTION - X/Y INT/FLOAT
         ***************************************************************/
        assertDoubleEquals(16, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(8, 8, 8, 8), EAST, NORTH, 1));
        assertDoubleEquals(15.5, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(8.5f, 8, 8, 8), EAST, NORTH, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(32, 8, 8, 8), EAST, NORTH, 1));

        assertDoubleEquals(8, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(32, 8, 8, 8), WEST, NORTH, 1));
        assertDoubleEquals(7.5, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(31.5f, 8, 8, 8), WEST, NORTH, 1));

        assertDoubleEquals(8, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(24, 16, 8, 8), SOUTH, WEST, 1));
        assertDoubleEquals(7.5f, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(24, 15.5f, 8, 8), SOUTH, WEST, 1));

        assertDoubleEquals(8, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(24, 16, 8, 8), SOUTH, EAST, 1));
        assertDoubleEquals(7.5f, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(24, 15.5f, 8, 8), SOUTH, EAST, 1));

        /***************************************************************
         * PATH LENGTH BEFORE DIRECTION - BOUNDS TEST
         ***************************************************************/
        assertDoubleEquals(0, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(24, 8, 8, 8), EAST, NORTH, 1));
        assertDoubleEquals(1, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(23, 8, 8, 8), EAST, NORTH, 1));
        assertDoubleEquals(0, MazeMoveHelper.getPathLengthBeforeDirection(maze, new Rectangle(25, 8, 8, 8), EAST, NORTH, 1));

    }

    private Maze mockMaze() {
        int layerIndex = 1;

        Maze maze = MazeMockFactory.mock(new Point(6, 4));
        when(maze.getLayerPath()).thenReturn(layerIndex);

        when(maze.isPath(new Point(1, 1), 1)).thenReturn(true);
        when(maze.isPath(new Point(2, 1), 1)).thenReturn(true);
        when(maze.isPath(new Point(3, 1), 1)).thenReturn(true);
        when(maze.isPath(new Point(4, 1), 1)).thenReturn(true);
        when(maze.isPath(new Point(3, 2), 1)).thenReturn(true);
        List<Point> paths = new ArrayList<Point>();

        paths.add(new Point(1, 1));
        paths.add(new Point(2, 1));
        paths.add(new Point(3, 1));
        paths.add(new Point(4, 1));
        paths.add(new Point(3, 2));
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                Point p = new Point(i, j);
                TiledMapTileLayer.Cell cell = mock(TiledMapTileLayer.Cell.class);
                when(maze.isPath(p, 1)).thenReturn(paths.contains(p));
                when(maze.getCell(layerIndex, p)).thenReturn(cell);
                when(maze.isPath(cell, 1)).thenReturn(paths.contains(p));
            }
        }
        return maze;
    }
}
