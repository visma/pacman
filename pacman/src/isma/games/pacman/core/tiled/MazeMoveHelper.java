package isma.games.pacman.core.tiled;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Set;

import isma.games.Direction;
import isma.games.Log;
import isma.games.NumberHelper;
import isma.games.Point;
import isma.games.PointCache;
import isma.games.TiledMapHelper;

import static isma.games.Direction.EAST;
import static isma.games.Direction.WEST;
import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.TiledMapHelper.getTilesAt;
import static isma.games.TiledMapHelper.handleOutOfBounds;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class MazeMoveHelper {
    private MazeMoveHelper() {
    }

    private static boolean isOnRails(Rectangle center, Direction direction) {
        if (direction.isHorizontal() && !NumberHelper.isInteger(center.y)) {
            return false;
        }
        return !(direction.isVertical() && !NumberHelper.isInteger(center.x));
    }

    public static float getPathLength(Maze maze, Rectangle center, Direction direction, int pathForce) {
        if (!isOnRails(center, direction)) {
            return 0;
        }
        Rectangle currCenter = new Rectangle(center);
        switch (direction) {
            case EAST:
                currCenter.x = (float) ceil(center.x) + 0;
                break;
            case SOUTH:
                currCenter.y = (float) floor(center.y) + 0;
                break;
            case WEST:
                currCenter.x = (float) floor(center.x) + 0;
                break;
            case NORTH:
                currCenter.y = (float) ceil(center.y) + 0;
                break;
        }
//        trace("currCenter{x=%s, y=%s}", center.x, center.y);
        Set<TiledMapTileLayer.Cell> cells = getTilesAt(maze.getLayerPath(), maze, currCenter, direction);
//        trace("tiles : " + cells.size());
        if (cells.size() > 1) {
            return 0;
        }
        float length = 0;
        if (maze.isPath(cells.iterator().next(), pathForce)) {
            if ((direction == WEST) || (direction == EAST)) {
                length += abs(currCenter.x - center.x);
            } else {
                length += abs(currCenter.y - center.y);
            }
        } else {
            return 0;
        }
        while (true) {
            switch (direction) {
                case EAST:
                    currCenter.x++;
                    break;
                case SOUTH:
                    currCenter.y--;
                    break;
                case WEST:
                    currCenter.x--;
                    break;
                case NORTH:
                    currCenter.y++;
                    break;
            }
//            trace("bounds " + maze.getBounds());
//            trace("test next pixel : " + currCenter);
//            trace("test next pixel (after bounds handling) : " + currCenter);
            cells = getTilesAt(maze.getLayerPath(), maze, currCenter, direction);
            if (cells.size() > 1) {
                return length;
            }
            if (maze.isPath(cells.iterator().next(), pathForce)) {
                length++;
            } else {
//                trace("length = " + length);
                return length;
            }
        }
    }

    public static float getPathLengthBeforeDirection(Maze maze,
                                                     Rectangle center,
                                                     Direction currentDirection,
                                                     Direction nextDirection, int pathForce) {
        Log.start(Log.LOG_TRACE);
//        trace("center{x=%s, y=%s}, currentDirection=%s, nextDirection=%s", center.x, center.y, currentDirection, nextDirection);
        Point originalPosition = getGridPosition(maze, new Vector2(center.x, center.y));
        Point nextPosition = originalPosition;
//        trace("position origine : " + originalPosition);
        while (true) {
            Point askedPosition = nextPosition.onNext(nextDirection);
            boolean askedPath = maze.isPath(askedPosition, pathForce);
//            trace("(asked) path vers %s sur %s : %s", nextDirection, askedPosition, askedPath);
            if (askedPath) {
                break;
            }
            nextPosition = nextPosition.onNext(currentDirection);
            boolean currPath = maze.isPath(nextPosition, pathForce);
//            trace("(current) path vers %s sur %s : %s", currentDirection, nextPosition, currPath);
            if (!currPath) {
                nextPosition = nextPosition.onPrevious(currentDirection);
                break;
            }
        }
        Vector2 initialPosition = new Vector2(center.x, center.y);
        Vector2 destination = TiledMapHelper.getPosition(maze, nextPosition);
//        trace("--------> position origine : " + originalPosition);
//        trace("--------> future position : " + nextPosition);
        Vector2 vLength = destination.sub(initialPosition);
//        trace("--------> vecteur distance : " + vLength);
        float len = -1f;
        switch (currentDirection) {
            case EAST:
                len = vLength.x;
                break;
            case SOUTH:
                len = -vLength.y;
                break;
            case WEST:
                len = -vLength.x;
                break;
            case NORTH:
                len = vLength.y;
                break;
        }
        len = len > 0 ? len : 0;
//        trace("--------> distance : " + len);
        return len;
    }

    public static Point nextTurnTileOn(Maze maze, Point position, Direction direction, Direction turn, int pathForce) {
        Point next = position;
        while (maze.isPath(next, pathForce)) {
            next = next.onNext(direction);
            next = handleOutOfBounds(maze, next);
            if (maze.isPath(next.onNext(turn), pathForce)){
                return next.onNext(turn).onNext(turn);
            }
        }
        return next.onPrevious(direction);
    }


    private static boolean isPath(Point position, Maze maze, boolean truncateBounds, int pathForce) {
        if (truncateBounds) {
            position = TiledMapHelper.handleOutOfBounds(maze, position);
        }
        return maze.isPath(position, pathForce);
    }

    public static Point getClosestPathOf(Maze maze, Point point, int pathForce, int radius) {
        point = TiledMapHelper.handleOutOfBounds(maze, point);
        if (maze.isPath(point, pathForce)) {
            return point;
        }
        for (int i = 1; i <= radius; i++) {
            int minX = point.x - i;
            int maxX = point.x + i;
            int minY = point.y - i;
            int maxY = point.y + i;

            for (int x = minX; x <= maxX; x++) {
                Point curr = PointCache.get(x, minY);
                if (isPath(curr, maze, true, pathForce)) {
                    return curr;
                }
                curr = PointCache.get(x, maxY);
                if (isPath(curr, maze, true, pathForce)) {
                    return curr;
                }
            }
            for (int y = minY; y <= maxY; y++) {
                Point curr = PointCache.get(minX, y);
                if (isPath(curr, maze, true, pathForce)) {
                    return curr;
                }
                curr = PointCache.get(maxX, y);
                if (isPath(curr, maze, true, pathForce)) {
                    return curr;
                }
            }
        }
        return null;
    }


    public static int countTurns(Maze maze, Point position, int pathForce) {
        int turns = 0;
        Point left = position.onLeft();
        Point right = position.onRight();
        Point bottom = position.onBottom();
        Point top = position.onTop();
        left = handleOutOfBounds(maze, left);
        right = handleOutOfBounds(maze, right);
        bottom = handleOutOfBounds(maze, bottom);
        top = handleOutOfBounds(maze, top);

        turns += maze.isPath(left, pathForce) ? 1 : 0;
        turns += maze.isPath(right, pathForce) ? 1 : 0;
        turns += maze.isPath(bottom, pathForce) ? 1 : 0;
        turns += maze.isPath(top, pathForce) ? 1 : 0;
        return turns;
    }


}
