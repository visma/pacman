package isma.games.pacman.core.tiled;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Point;
import isma.games.TileMapProperties;
import isma.games.TiledMapWrapper;
import isma.games.TiledPropertiesCache;

import static isma.games.pacman.core.tiled.Maze.LAYER_PATH;

class MazeProperties extends TileMapProperties {
    //Properties
    private static final String PROPERTY_PATH = "path";
    private static final String PROPERTY_DOT = "pacgum";
    private static final String PROPERTY_ENERGIZER = "energizer";

    private final int layerPathIndex;
    private int layerDotIndex;

    private final boolean[] path1Cache;
    private final boolean[] path2Cache;

    MazeProperties(TiledMapWrapper map, int layerPathIndex, int layerDotIndex) {
        super(map);
        path1Cache = initPathCache(1);
        path2Cache = initPathCache(2);
        this.layerPathIndex = layerPathIndex;
        this.layerDotIndex = layerDotIndex;
    }

    private boolean[] initPathCache(int pathForce) {
        boolean[] pathCache = new boolean[map.getWidth() * map.getHeight()];
        for (int i = 0; i < pathCache.length; i++) {
            pathCache[i] = false;
        }
        TiledPropertiesCache.Key key = new TiledPropertiesCache.Key(LAYER_PATH, PROPERTY_PATH);
        ArrayMap<Point, String> pathPoints = propertiesCache.getCache().get(key);
        for (Point point : pathPoints.keys()) {
            int currentPathForce = Integer.parseInt(pathPoints.get(point));
            if (currentPathForce <= pathForce) {
                pathCache[map.getWidth() * point.y + point.x] = true;
            }
        }
        return pathCache;
    }

    boolean isPath(TiledMapTileLayer.Cell cell, int pathForce) {
        return isPath(pathForce, map.getProperty(cell, PROPERTY_PATH));
    }

    boolean isPath(Point gridPosition, int pathForce) {
        if (pathForce == 2) {
            return path2Cache[map.getWidth() * gridPosition.y + gridPosition.x];
        }
        return path1Cache[map.getWidth() * gridPosition.y + gridPosition.x];
    }

    private boolean isPath(int pathForce, String pathProperty) {
        if (pathProperty.equals("")) {
            return false;
        }
        if (pathProperty.equals("1") && pathForce >= 1) {
            return true;
        }
        return pathForce == 2 && "2".equals(pathProperty);
    }

    boolean isDot(Point gridPosition) {
        return "1".equals(map.getProperty(layerDotIndex, PROPERTY_DOT, gridPosition));
    }

    boolean isEnergizer(Point gridPosition) {
        return "1".equals(map.getProperty(layerDotIndex, PROPERTY_ENERGIZER, gridPosition));
    }


}
