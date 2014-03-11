package isma.games.test;

import com.badlogic.gdx.math.Rectangle;
import isma.games.Point;
import isma.games.pacman.core.tiled.Maze;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class MazeMockFactory {
    private MazeMockFactory() {
    }

    public static Maze mock(Point bounds) {
        int tileSize = 8;

        Maze maze = Mockito.mock(Maze.class);
        when(maze.getTileHeight()).thenReturn(tileSize);
        when(maze.getTileWidth()).thenReturn(tileSize);
        when(maze.getWidth()).thenReturn(bounds.x);
        when(maze.getHeight()).thenReturn(bounds.y);
        when(maze.getBounds()).thenReturn(new Rectangle(0, 0, bounds.x, bounds.y));
        return maze;
    }
}
