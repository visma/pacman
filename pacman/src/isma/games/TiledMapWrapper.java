package isma.games;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class TiledMapWrapper {
    protected final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;


    protected TiledMapWrapper(OrthographicCamera camera, TiledMap map, int visibleLayer) {
        this.map = map;
        setLayerVisible(visibleLayer);

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapRenderer.setView(camera);

        MapProperties prop = map.getProperties();

        width = prop.get("width", Integer.class);
        height = prop.get("height", Integer.class);
        tileWidth = prop.get("tilewidth", Integer.class);
        tileHeight = prop.get("tileheight", Integer.class);
        PointCache.init(width, height);
    }

    public String getProperty(int layerIndex, String propertyName, Point gridPosition) {
        TiledMapTileLayer.Cell cell = getCell(layerIndex, gridPosition);
        return getProperty(cell, propertyName);
    }

    public String getProperty(TiledMapTileLayer.Cell cell, String propertyName) {
        String noProperty = "";
        if (cell == null) {
            return noProperty;
        }
        Object val = cell.getTile().getProperties().get(propertyName);
        return val == null ? noProperty : val.toString();
    }

    public TiledMapTileLayer.Cell getCell(int layerIndex, Point gridPosition) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        return layer.getCell(gridPosition.x, gridPosition.y);
    }

    private void setLayerVisible(int layerIndex) {
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            map.getLayers().get(i).setVisible(i == layerIndex);
        }
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose() {
        mapRenderer.dispose();
        map.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, getWidth(), getHeight());
    }



    public boolean isOutOfBounds(Point position) {
        Rectangle bounds = getBounds();
        return position.x >= bounds.x && position.x <= (bounds.x + bounds.width - 1)
                && position.y >= bounds.y && position.y <= (bounds.y + bounds.height - 1);
    }

    /********************************************************************************
     *  GETTERS/SETTERS
     ********************************************************************************/
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    TiledMap getWrapped() {
        return map;
    }

}
