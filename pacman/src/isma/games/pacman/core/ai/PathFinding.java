package isma.games.pacman.core.ai;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import isma.games.Direction;
import isma.games.Point;
import isma.games.PositionHelper;
import isma.games.Target;
import isma.games.TiledMapWrapper;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.AliveActor;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.pacman.core.manager.MoveHandlerHelper.nextCenterPosition;


public class PathFinding {

    public static Direction nextDirection(Array<Vertex<Point>> path,
                                          TiledMapWrapper map,
                                          AliveActor actor,
                                          float remainingLen) {
        Target nextDefaultPosition = nextCenterPosition(map, actor, remainingLen);
        Point gridPosition = getGridPosition(map, nextDefaultPosition);

        Array<Vertex<Point>> newPath = new Array<Vertex<Point>>(path);
//        debug("nextActorPosition : " + gridPosition);
//        debug("path : " + path);
        //TODO fix pour eviter que l'ia loupe un virage et revienne je crois
        if (path.size > 2 && path.contains(new Vertex<Point>(gridPosition), false)) {
            int pathBeginIndex = path.indexOf(new Vertex<Point>(gridPosition), false);
//            debug("actor tile on path index : " + pathBeginIndex);
            if (pathBeginIndex > 0) {
                //newPath.clear();
                newPath.addAll(path, pathBeginIndex, path.size - pathBeginIndex);
                //newPath.addAll(path.subList(pathBeginIndex, path.size()));
                path = newPath;
//                debug("newPath : " + path);
            }
        }
//        debug("path.size : " + path.size());
        if (path.size < 2) {
            return actor.getCurrentDirection();
        }
        Vertex<Point> source = path.get(0);
        Vertex<Point> nextDestination = path.get(1);
//        debug("source=%s, nextDestination=%s", source, nextDestination);
        Direction direction = getDirection(source, nextDestination, map.getBounds());
//        debug("direction=%s", direction);
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