package isma.games.pacman.core.ai;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import isma.games.Direction;
import isma.games.Point;
import isma.games.PositionHelper;
import isma.games.Target;
import isma.games.TiledMapWrapper;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.ActorLog;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeMoveHelper;
import isma.games.utils.TargetUtils;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.pacman.core.manager.MoveHandlerHelper.nextCenterPosition;


public class PathFinding {

    //TODO code moche pas stable : a tester à donf
    public static Direction nextDirection(Array<Vertex<Point>> path,
                                          Maze map,
                                          AliveActor actor,
                                          float remainingLen) {
        Target nextDefaultPosition = nextCenterPosition(map, actor, remainingLen);
        Point gridPosition = getGridPosition(map, nextDefaultPosition);

        Array<Vertex<Point>> newPath = new Array<Vertex<Point>>(path);
        if (path.size > 2 && path.contains(new Vertex<Point>(gridPosition), false)) {
            if (MazeMoveHelper.countTurns(map, gridPosition, 2) == 1) {
                //dead end keep current position on path

            } else {
                int pathBeginIndex = path.indexOf(new Vertex<Point>(gridPosition), false);
                if (pathBeginIndex != -1) {
                    newPath.clear();
                    int offset = pathBeginIndex + 1;
                    int length = path.size - pathBeginIndex - 1;
                    newPath.addAll(path, offset, length);
                    path = newPath;
                }
            }
        }
        if (path.size < 2) {
            return actor.getCurrentDirection();
        }
        Vertex<Point> source = path.get(0);
        Vertex<Point> nextDestination = path.get(1);
        Direction direction = getDirection(source, nextDestination, map.getBounds());
//        ActorLog.logIfPinky(actor, "source=%s, nextDestination=%s, direction=%s, absolutePos=%s",
//                source, nextDestination, direction, TargetUtils.stringify(actor));
        return direction;
    }

    private static Direction getDirection(Vertex<Point> vertex, Vertex<Point> closestVertex, Rectangle bounds) {
        Array<Direction> orientations = PositionHelper.getOrientation(vertex.id, closestVertex.id);
        if (orientations.size != 1) {
            throw new RuntimeException("?");
        }
        if (hasChangeBound(vertex, closestVertex, bounds)) {
            return orientations.get(0).opposite();
        }
        return orientations.get(0);
    }

    public static boolean hasChangeBound(Vertex<Point> vertex, Vertex<Point> nextVertex, Rectangle bounds) {
        Point pointA = vertex.id;
        Point pointB = nextVertex.id;
        if (pointA.x == bounds.x && pointB.x == (bounds.width - 1)) {
            return true;
        }
        if (pointB.x == bounds.x && pointA.x == (bounds.width - 1)) {
            return true;
        }
        if (pointA.y == bounds.y && pointB.y == (bounds.height - 1)) {
            return true;
        }
        if (pointB.y == bounds.y && pointA.y == (bounds.height - 1)) {
            return true;
        }
        return false;
    }
}