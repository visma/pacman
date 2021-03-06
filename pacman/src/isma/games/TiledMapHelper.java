package isma.games;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Set;

public class TiledMapHelper {
    private TiledMapHelper() {
    }

    public static Set<Point> getTilesPointAt(TiledMapWrapper map,
                                             Rectangle rect,
                                             Direction direction) {
        float x1 = rect.x;
        float y1 = rect.y;
        float x2 = x1 + rect.width - 1;
        float y2 = y1 + rect.height - 1;

        Set<Point> cells = new HashSet<Point>();
        switch (direction) {
            case SOUTH:
                cells.add(getPointAt(map, x1, y1));
                cells.add(getPointAt(map, x2, y1));
                break;
            case WEST:
                cells.add(getPointAt(map, x1, y1));
                cells.add(getPointAt(map, x1, y2));
                break;
            case NORTH:
                cells.add(getPointAt(map, x1, y2));
                cells.add(getPointAt(map, x2, y2));
                break;
            case EAST:
                cells.add(getPointAt(map, x2, y1));
                cells.add(getPointAt(map, x2, y2));
                break;
        }
        if (cells.isEmpty()) {
            throw new RuntimeException("not tile found !");
        }
        return cells;
    }


       public static Point getPointAt(TiledMapWrapper map, float x, float y) {
        Point gridPosition = getGridPosition(map, x, y);
        gridPosition = TiledMapHelper.handleOutOfBounds(map, gridPosition);
        return gridPosition;
    }


    public static Point getGridPosition(TiledMapWrapper map, Vector2 position) {
        return getGridPosition(map, position.x, position.y);
    }

    public static Point getGridPosition(TiledMapWrapper map, float absoluteX, float absoluteY) {
        int gridX = (int) (absoluteX / map.getTileWidth());
        int gridY = (int) ((absoluteY) / map.getTileHeight());
        if (absoluteX < 0) {
            gridX -= 1;
        }
        if (absoluteY < 0) {
            gridY -= 1;
        }
        return PointCache.get(gridX, gridY);
    }

    public static Point getGridPosition(TiledMapWrapper mapWrapper, Target target) {
        int gridX = (int) (target.getCenter().getX() / mapWrapper.getTileWidth());
        int gridY = (int) ((target.getCenter().getY()) / mapWrapper.getTileHeight());
        if (target.getCenter().getX() < 0) {
            gridX -= 1;
        }
        if (target.getCenter().getY() < 0) {
            gridY -= 1;
        }
        return PointCache.get(gridX, gridY);
    }

    public static Vector2 getPosition(TiledMapWrapper map, Point gridPosition) {
        return new Vector2(
                gridPosition.x * map.getTileWidth(),
                gridPosition.y * map.getTileHeight()
        );
    }

    public static void handleOutOfBounds(TiledMapWrapper map, Rectangle center) {
        float boundX1 = map.getBounds().x;
        float boundY1 = map.getBounds().y;
        float boundX2 = map.getBounds().width * map.getTileWidth();
        float boundY2 = map.getBounds().height * map.getTileHeight();

        if (center.x < boundX1 || center.x >= boundX2) {
//            trace("x=%s out of bounds : (bounds={%s, %s} ", center.x, boundX1, boundX2);
            if (center.x < boundX1) {
                center.x = boundX2 + center.x;
            } else {
                center.x = center.x - boundX2;
            }
        }
        if (center.y < boundY1 || center.y >= boundY2) {
//            trace("y=%s out of bounds : (bounds={%s, %s} ", center.y, boundY1, boundY2);
            if (center.y < boundY1) {
                center.y = boundY2 + center.y;
            } else {
                center.y = center.y - boundY2;
            }
        }
    }

    public static void handleOutOfBounds(TiledMapWrapper map, GameObject obj) {
        float boundX1 = map.getBounds().x;
        float boundY1 = map.getBounds().y;
        float boundX2 = map.getBounds().width * map.getTileWidth();
        float boundY2 = map.getBounds().height * map.getTileHeight();

        Rectangle center = obj.getCenter();

        float newCenterX = center.x;
        float newCenterY = center.y;
        if (center.getX() < boundX1 || center.getX() >= boundX2) {
//            trace("x=%s out of bounds : (bounds={%s, %s} ", center.getX(), boundX1, boundX2);
            if (center.getX() < boundX1) {
                newCenterX = boundX2 + obj.getCenter().x;
            } else {
                newCenterX = obj.getCenter().x - boundX2;
            }
        }
        if (obj.getY() < boundY1 || obj.getY() >= boundY2) {
//            trace("y=%s out of bounds : (bounds={%s, %s} ", obj.getY(), boundY1, boundY2);
            float centerY;
            if (obj.getY() < boundY1) {
                newCenterY = boundY2 + obj.getCenter().y;
            } else {
                newCenterY = obj.getCenter().y - boundY2;
            }
        }
        obj.setX(obj.getX() + (newCenterX - center.x));
        obj.setY(obj.getY() + (newCenterY - center.y));
    }

    public static Point handleOutOfBounds(TiledMapWrapper map, Point gridPosition) {
        int boundX1 = (int) map.getBounds().x;
        int boundY1 = (int) map.getBounds().y;
        int boundX2 = (int) map.getBounds().width;
        int boundY2 = (int) map.getBounds().height;

        int x = gridPosition.x;
        int y = gridPosition.y;
        if (gridPosition.x < boundX1 || gridPosition.x >= boundX2) {
//            trace("x=%s out of bounds : (bounds={%s, %s} ", gridPosition.x, boundX1, boundX2);
            if (gridPosition.x < boundX1) {
                x = boundX2 + gridPosition.x;
            } else {
                x = gridPosition.x - boundX2;
            }
        }
        if (gridPosition.y < boundY1 || gridPosition.y >= boundY2) {
//            trace("y=%s out of bounds : (bounds={%s, %s} ", gridPosition.y, boundY1, boundY2);
            if (gridPosition.y < boundY1) {
                y = boundY2 + gridPosition.y;
            } else {
                y = gridPosition.y - boundY2;
            }
        }
        return PointCache.get(x, y);
    }

    public static Target getTarget(TiledMapWrapper map, Point point) {
        return new SimpleTarget(point.x * map.getTileWidth(), point.y * map.getTileHeight(), map.getTileWidth(), map.getTileHeight());
    }
}
