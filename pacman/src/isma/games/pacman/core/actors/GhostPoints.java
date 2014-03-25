package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.pacman.core.assets.Assets;

public class GhostPoints extends Actor {
    private final TextureRegion[] ghostPoints;
    private ArrayMap<Vector2, Integer> map = new ArrayMap<Vector2, Integer>();

    public GhostPoints() {
        Texture t = Assets.TEXTURE_GHOST_POINTS;
        ghostPoints = TextureRegion.split(t, t.getWidth() / 4, t.getHeight())[0];
    }


    @Override
    public final void draw(Batch batch, float parentAlpha) {
        for (Vector2 key : map.keys()) {
            Integer position = map.get(key);
            batch.draw(ghostPoints[position - 1],
                    key.x * Assets.configuration.getScaleRatio(),
                    key.y * Assets.configuration.getScaleRatio());
        }
    }

    public void add(int ghostCount, Vector2 ghostPosition) {
        map.put(ghostPosition, ghostCount);
    }

    public void remove(Vector2 ghostPosition) {
        map.removeKey(ghostPosition);
    }
}
