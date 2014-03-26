package isma.games;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.tiled.Maze;

public abstract class TiledMapWrapper {
    protected final TiledMap map;
    //OrthogonalTiledMapRenderer is too slow at this time : reactivate it if a boosted version come...
    //private final OrthogonalTiledMapRenderer mapRenderer;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private SpriteBatch spriteBatch;


    protected TiledMapWrapper(TiledMap map, int visibleLayer, OrthographicCamera camera) {
        this.map = map;
        setLayerVisible(visibleLayer);
        //OrthogonalTiledMapRenderer is too slow at this time : reactivate it if a boosted version come...
        //mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.configuration.getScaleRatio());
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.projection);

        MapProperties prop = map.getProperties();

        width = prop.get("width", Integer.class);
        height = prop.get("height", Integer.class);
        tileWidth = prop.get("tilewidth", Integer.class);
        tileHeight = prop.get("tileheight", Integer.class);
        PointCache.init(width, height);
    }

    //OK
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

    /**
     * OrthogonalTiledMapRenderer is very slow : draw texture background instead (possible because small map and no scroll)
     */
    public void render(OrthographicCamera camera) {
        //OrthogonalTiledMapRenderer is too slow at this time : reactivate it if a boosted version come...
        //mapRenderer.render();

        spriteBatch.begin();
        spriteBatch.draw(Assets.TEXTURE_MAZE,
                -width * tileWidth,
                -height * tileHeight);
        spriteBatch.end();
    }

    public void dispose() {
        map.dispose();
        spriteBatch.dispose();
        //OrthogonalTiledMapRenderer is too slow at this time : reactivate it if a boosted version come...
        // mapRenderer.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, getWidth(), getHeight());
    }


    public boolean isOutOfBounds(Point position) {
        Rectangle bounds = getBounds();
        return position.x >= bounds.x && position.x <= (bounds.x + bounds.width - 1)
                && position.y >= bounds.y && position.y <= (bounds.y + bounds.height - 1);
    }

    /**
     * *****************************************************************************
     * GETTERS/SETTERS
     * ******************************************************************************
     */
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

    //OrthogonalTiledMapRenderer is too slow at this time : reactivate it if a boosted version come...
    /*public TiledMapRenderer getRenderer() {
        return mapRenderer;
    }*/
}
