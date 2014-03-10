package isma.games;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Set;

import static isma.games.Log.trace;

public class TiledMapHelper {
    private TiledMapHelper() {
    }

    public static Set<TiledMapTileLayer.Cell> getTilesAt(int layerIndex,
                                                         TiledMapWrapper map,
                                                         Rectangle rect,
                                                         Direction direction) {
        float x1 = rect.getX();
        float y1 = rect.getY();
        float x2 = x1 + rect.getWidth() - 1;
        float y2 = y1 + rect.getHeight() - 1;

        Set<TiledMapTileLayer.Cell> cells = new HashSet<TiledMapTileLayer.Cell>();
        switch (direction) {
            case SOUTH:
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y1)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y1)));
                break;
            case WEST:
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y1)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y2)));
                break;
            case NORTH:
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y2)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y2)));
                break;
            case EAST:
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y1)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y2)));
                break;
        }
        if (cells.isEmpty()) {
            throw new RuntimeException("not tile found !");
        }
        return cells;
    }

    public static Set<TiledMapTileLayer.Cell> getTilePositionAt(int layerIndex,
                                                                TiledMapWrapper map,
                                                                Rectangle rect,
                                                                Direction direction) {
        float x1 = rect.getX();
        float y1 = rect.getY();
        float x2 = x1 + rect.getWidth() - 1;
        float y2 = y1 + rect.getHeight() - 1;

        Set<TiledMapTileLayer.Cell> cells = new HashSet<TiledMapTileLayer.Cell>();
        switch (direction) {
            case SOUTH:
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y1)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y1)));
                break;
            case WEST:
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y1)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y2)));
                break;
            case NORTH:
                cells.add(getCellAt(layerIndex, map, new Vector2(x1, y2)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y2)));
                break;
            case EAST:
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y1)));
                cells.add(getCellAt(layerIndex, map, new Vector2(x2, y2)));
                break;
        }
        if (cells.isEmpty()) {
            throw new RuntimeException("not tile found !");
        }
        return cells;
    }

    public static TiledMapTileLayer.Cell getCellAt(int layerIndex, TiledMapWrapper map, Vector2 vector2) {
        Point gridPosition = getGridPosition(map, vector2);
        TiledMapHelper.handleOutOfBounds(map, gridPosition);
        return map.getCell(layerIndex, gridPosition);
    }

    public static Point getGridPosition(TiledMapWrapper map, Vector2 position) {
        int gridX = (int) (position.x / map.getTileWidth());
        int gridY = (int) ((position.y) / map.getTileHeight());
        if (position.x < 0) {
            gridX -= 1;
        }
        if (position.y < 0) {
            gridY -= 1;
        }
        return new Point(gridX, gridY);
    }

    public static Point getGridPosition(TiledMapWrapper mapWrapper, Target target) {
        int gridX = (int) (target.getCenter().getX() / mapWrapper.getTileWidth());
        int gridY = (int) ((target.getCenter().getY()) / mapWrapper.getTileHeight());
        return new Point(gridX, gridY);
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
            trace("x=%s out of bounds : (bounds={%s, %s} ", center.x, boundX1, boundX2);
            if (center.x < boundX1) {
                center.x = boundX2 + center.x;
            } else {
                center.x = center.x - boundX2;
            }
        }
        if (center.y < boundY1 || center.y >= boundY2) {
            trace("y=%s out of bounds : (bounds={%s, %s} ", center.y, boundY1, boundY2);
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
            trace("x=%s out of bounds : (bounds={%s, %s} ", center.getX(), boundX1, boundX2);
            if (center.getX() < boundX1) {
                newCenterX = boundX2 + obj.getCenter().x;
                //obj.setX(newCenterX);
            } else {
                newCenterX = obj.getCenter().x - boundX2;
                //obj.setX(newCenterX);
            }
        }
        if (obj.getY() < boundY1 || obj.getY() >= boundY2) {
            trace("y=%s out of bounds : (bounds={%s, %s} ", obj.getY(), boundY1, boundY2);
            float centerY;
            if (obj.getY() < boundY1) {
                newCenterY = boundY2 + obj.getCenter().y;
                //obj.setY(newCenterY);
            } else {
                newCenterY = obj.getCenter().y - boundY2;
                //obj.setY(newCenterY);
            }
        }
        obj.setX(obj.getX() + (newCenterX - center.x));
        obj.setY(obj.getY() + (newCenterY - center.y));
    }

    public static void handleOutOfBounds(TiledMapWrapper map, Point gridPosition) {
        int boundX1 = (int) map.getBounds().x;
        int boundY1 = (int) map.getBounds().y;
        int boundX2 = (int) map.getBounds().width;
        int boundY2 = (int) map.getBounds().height;

        if (gridPosition.x < boundX1 || gridPosition.x >= boundX2) {
            trace("x=%s out of bounds : (bounds={%s, %s} ", gridPosition.x, boundX1, boundX2);
            if (gridPosition.x < boundX1) {
                gridPosition.x = boundX2 + gridPosition.x;
            } else {
                gridPosition.x = gridPosition.x - boundX2;
            }
        }
        if (gridPosition.y < boundY1 || gridPosition.y >= boundY2) {
            trace("y=%s out of bounds : (bounds={%s, %s} ", gridPosition.y, boundY1, boundY2);
            if (gridPosition.y < boundY1) {
                gridPosition.y = boundY2 + gridPosition.y;
            } else {
                gridPosition.y = gridPosition.y - boundY2;
            }
        }
    }

    //TODO l'utiliser quand des SimpleTarget sont fabriquées manuellement
    public static Target getTarget(TiledMapWrapper map, Point point) {
        Vector2 position = getPosition(map, point);
        return new SimpleTarget(position.x, position.y, map.getTileWidth(), map.getTileHeight());
    }
}
