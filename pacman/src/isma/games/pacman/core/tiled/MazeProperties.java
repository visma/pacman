package isma.games.pacman.core.tiled;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import isma.games.Point;
import isma.games.TileMapProperties;
import isma.games.TiledMapWrapper;

class MazeProperties extends TileMapProperties {
    //Properties
    private static final String PROPERTY_PATH = "path";
    private static final String PROPERTY_DOT = "pacgum";
    private static final String PROPERTY_ENERGIZER = "energizer";

    private final int layerPathIndex;
    private int layerDotIndex;

    MazeProperties(TiledMapWrapper map, int layerPathIndex, int layerDotIndex) {
        super(map);
        this.layerPathIndex = layerPathIndex;
        this.layerDotIndex = layerDotIndex;
    }

    boolean isPath(TiledMapTileLayer.Cell cell, int pathForce) {
        return isPath(pathForce, map.getProperty(cell, PROPERTY_PATH));
    }

    boolean isPath(Point gridPosition, int pathForce) {
        return isPath(pathForce, map.getProperty(layerPathIndex, PROPERTY_PATH, gridPosition));
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
