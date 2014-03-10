package isma.games.pacman.core.tiled;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import isma.games.Point;
import isma.games.TiledMapWrapper;
import isma.games.graph.PathMap;

public class Maze extends TiledMapWrapper implements PathMap {
    public static final int WIDTH = 224;
    public static final int HEIGHT = 288;
    //Layers
    public static final int LAYER_MAIN = 0;
    public static final int LAYER_DOT = 1;
    public static final int LAYER_PATH = 2;

    private final MazeProperties properties;

    Maze(OrthographicCamera camera, TiledMap map, int layerIndex) {
        super(camera, map, layerIndex);
        properties = new MazeProperties(this, getLayerPath(), getLayerDot());
    }

    public Point getGhostCorner(String name, Integer cornerIndex) {
        return properties.getUniqueKeyValuePosition(getLayerPath(), name, cornerIndex.toString());
    }

    public Point getGhostHouse() {
        return properties.getUniqueKeyValuePosition(getLayerPath(), "house", "1");
    }

    public Point getFruitPosition() {
        return properties.getUniqueKeyValuePosition(getLayerDot(), "fruitSpawnPoint", "1");
    }


    public boolean isPath(TiledMapTileLayer.Cell cell, int pathForce) {
        return properties.isPath(cell, pathForce);
    }

    public boolean isPath(Point gridPosition, int pathForce) {
        return properties.isPath(gridPosition, pathForce);
    }

    public boolean isPath(Point gridPosition) {
        return properties.isPath(gridPosition, 1);
    }


    public boolean isDot(Point gridPosition) {
        return properties.isDot(gridPosition);
    }

    public boolean isEnergizer(Point gridPosition) {
        return properties.isEnergizer(gridPosition);
    }


    public int getLayerPath() {
        return LAYER_PATH;
    }

    public int getLayerDot() {
        return LAYER_DOT;
    }


    public boolean isCorner(Point position) {
        boolean path = isPath(position);
        if (!path) {
            return false;
        }
        boolean topPath = isPath(position.onTop());
        boolean bottomPath = isPath(position.onBottom());
        boolean leftPath = isPath(position.onLeft());
        boolean rightPath = isPath(position.onRight());

        if (topPath && !bottomPath) {
            return leftPath && !rightPath || rightPath && !leftPath;
        }
        if (bottomPath && !topPath) {
            return leftPath && !rightPath || rightPath && !leftPath;
        }
        return false;
    }


}
