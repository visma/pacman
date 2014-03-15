package isma.games.pacman.core.ai;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import isma.games.Direction;
import isma.games.Point;
import isma.games.PointCache;
import isma.games.TiledMapWrapper;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PathFindingTest {
    @Test
    public void nextDirection_same_direction_after_bound() throws Exception {
        /**
         * Visual Path is :
         *-------
         *  ooo
         *-------
         */
        Array<Vertex<Point>> path = new Array<Vertex<Point>>();
        path.add(new Vertex<Point>(PointCache.get(0, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 0)));
        path.add(new Vertex<Point>(PointCache.get(2, 2)));

        AliveActor actor = mockActor(23.5f, 0, EAST);
        Maze tiledMap = mockTiledMap(new Rectangle(0, 0, 3, 3));
        float remainingLen = 1.0f;

        Direction nextDirection = PathFinding.nextDirection(path, tiledMap, actor, remainingLen);
        Assert.assertEquals(EAST, nextDirection);
    }

    @Test
    public void nextDirection_nextMove_justOn_turn() throws Exception {
        /**
         * Visual Path is :
         *-------
         *   o
         *  oox
         *-------
         */
        Array<Vertex<Point>> path = new Array<Vertex<Point>>();
        path.add(new Vertex<Point>(PointCache.get(0, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 1)));

        AliveActor actor = mockActor(7.5f, 0, EAST);
        Maze tiledMap = mockTiledMap(new Rectangle(0, 0, 3, 3));
        float remainingLen = 0.5f;

        Direction nextDirection = PathFinding.nextDirection(path, tiledMap, actor, remainingLen);
        Assert.assertEquals(NORTH, nextDirection);
    }

    @Test
    public void nextDirection_nextMove_after_turn() throws Exception {
        /**
         * Visual Path is :
         *-------
         *   o
         *  oox
         *-------
         */

        Array<Vertex<Point>> path = new Array<Vertex<Point>>();
        path.add(new Vertex<Point>(PointCache.get(0, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 1)));

        AliveActor actor = mockActor(7.5f, 0, EAST);
        Maze tiledMap = mockTiledMap(new Rectangle(0, 0, 3, 3));
        float remainingLen = 0.6f;

        Direction nextDirection = PathFinding.nextDirection(path, tiledMap, actor, remainingLen);
        Assert.assertEquals(NORTH, nextDirection);
    }

    @Test
    public void nextDirection_nextMove_before_turn() throws Exception {
        /**
         * Visual Path is :
         *-------
         *   o
         *  oox
         *-------
         */
        Array<Vertex<Point>> path = new Array<Vertex<Point>>();
        path.add(new Vertex<Point>(PointCache.get(0, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 0)));
        path.add(new Vertex<Point>(PointCache.get(1, 1)));

        AliveActor actor = mockActor(7.5f, 0, EAST);
        Maze tiledMap = mockTiledMap(new Rectangle(0, 0, 3, 3));
        float remainingLen = 0.4f;

        Direction nextDirection = PathFinding.nextDirection(path, tiledMap, actor, remainingLen);
        Assert.assertEquals(NORTH, nextDirection);
    }

    @Test
    //TODO changer signature en erreur et reactiver
    public void boundsChange() {
        //north bound to south bound
        assertTrue(PathFinding.hasChangeBound(
                new Vertex<Point>(PointCache.get(0, 3)),
                new Vertex<Point>(PointCache.get(0, 0)),
                new Rectangle(0, 0, 1, 4)));
        //south bound to north bound
        assertTrue(PathFinding.hasChangeBound(
                new Vertex<Point>(PointCache.get(0, 0)),
                new Vertex<Point>(PointCache.get(0, 3)),
                new Rectangle(0, 0, 1, 4)));

        //right bound to left bound
        assertTrue(PathFinding.hasChangeBound(
                new Vertex<Point>(PointCache.get(3, 0)),
                new Vertex<Point>(PointCache.get(0, 0)),
                new Rectangle(0, 0, 4, 1)));

        //left bound to right bound
        assertTrue(PathFinding.hasChangeBound(
                new Vertex<Point>(PointCache.get(0, 0)),
                new Vertex<Point>(PointCache.get(3, 0)),
                new Rectangle(0, 0, 4, 1)));

        //no horizontal bound passed
        assertTrue(PathFinding.hasChangeBound(
                new Vertex<Point>(PointCache.get(2, 0)),
                new Vertex<Point>(PointCache.get(3, 0)),
                new Rectangle(0, 0, 5, 1)));

        //no vertical bound passed
        assertTrue(PathFinding.hasChangeBound(
                new Vertex<Point>(PointCache.get(0, 2)),
                new Vertex<Point>(PointCache.get(0, 3)),
                new Rectangle(0, 0, 1, 5)));

    }

    private Maze mockTiledMap(Rectangle bounds) {
        Maze tiledMap = mock(Maze.class);
        when(tiledMap.getBounds()).thenReturn(bounds);
        when(tiledMap.getTileHeight()).thenReturn(8);
        when(tiledMap.getTileWidth()).thenReturn(8);
        return tiledMap;
    }

    private AliveActor mockActor(float x, float y, Direction direction) {
        AliveActor actor = mock(AliveActor.class);
        when(actor.getCurrentDirection()).thenReturn(direction);
        when(actor.getX()).thenReturn(x);
        when(actor.getY()).thenReturn(y);
        when(actor.getId()).thenReturn("id");
        when(actor.getCenter()).thenReturn(new Rectangle(x + 4, y + 4, 8, 8));
        return actor;
    }

}
