package isma.games;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Iterator;

import static isma.games.Log.debug;
import static isma.games.Log.trace;

public class TileMapProperties {
    protected final TiledMapWrapper map;

    private TiledPropertiesCache cache = new TiledPropertiesCache();

    public TileMapProperties(TiledMapWrapper map) {
        this.map = map;
        loadProperties();
    }

    public Point getUniqueKeyValuePosition(int layerIndex, String key, String uniqueValue) {
        return cache.getPositionOfUniqueValue(layerIndex, key, uniqueValue);
    }

    private void loadProperties() {
        TiledMap mapWrapped = map.getWrapped();
        for (int layerIndex = 0; layerIndex < mapWrapped.getLayers().getCount(); layerIndex++) {
            for (int i = 0; i < map.getWidth(); i++) {
                for (int j = 0; j < map.getHeight(); j++) {
                    TiledMapTileLayer layer = (TiledMapTileLayer) mapWrapped.getLayers().get(layerIndex);
                    MapProperties properties = layer.getCell(i, j).getTile().getProperties();
                    Iterator<String> keys = properties.getKeys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String value = (String) properties.get(key);
                        debug("layer=%s, x=%s, y=%s, key=%s, value=%s", layerIndex, i, j, key, value);
                        cache.put(layerIndex, i, j, key, value);
                    }
                }
            }
        }
    }

}
