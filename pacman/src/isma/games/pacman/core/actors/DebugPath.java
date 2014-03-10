package isma.games.pacman.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import isma.games.Point;
import isma.games.graph.Vertex;
import isma.games.pacman.core.assets.Assets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DebugPath extends Actor {
    private final Map<String, TextureRegion> textures = new HashMap<String, TextureRegion>();
    private Map<AliveActor, LinkedList<Vertex<Point>>> paths = new HashMap<AliveActor, LinkedList<Vertex<Point>>>();
    private boolean active;

    public DebugPath(boolean active) {
        this.active = active;
        for (String id : Arrays.asList("pacman", "blinky", "inky", "clyde", "pinky")) {
            Texture spriteSheet = new Texture(Gdx.files.internal(Assets.getPathTraceSpriteSheet(id)));
            TextureRegion[][] textureRegions = TextureRegion.split(spriteSheet,
                    spriteSheet.getWidth() / 2,
                    spriteSheet.getHeight());
            textures.put(id, textureRegions[0][1]);
        }
    }


    @Override
    public final void draw(Batch batch, float parentAlpha) {
        if (!active || paths.isEmpty()) {
            return;
        }
        for (AliveActor aliveActor : paths.keySet()) {
            if (aliveActor instanceof Pacman){
                continue;
            }
            if (paths.get(aliveActor) == null){
                //Log.warn("pas de path pour %s ??", aliveActor.getId());
                continue;
            }
            for (Vertex vertex : paths.get(aliveActor)) {
                Point point = (Point) vertex.getId();
                batch.draw(textures.get(aliveActor.getId()), point.x * 8, point.y * 8);
            }
        }
    }

    public void setPath(AliveActor actor, LinkedList<Vertex<Point>> path) {
        paths.put(actor, path);
    }

}
