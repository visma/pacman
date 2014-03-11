package isma.games.pacman.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import isma.games.Point;
import isma.games.graph.Graph;
import isma.games.graph.Vertex;
import isma.games.pacman.core.assets.Assets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DebugPath extends Actor {
    private final Map<String, TextureRegion> actorTextures = new HashMap<String, TextureRegion>();
    private final Map<AliveActor, LinkedList<Vertex<Point>>> actorPaths = new HashMap<AliveActor, LinkedList<Vertex<Point>>>();
    private final Texture graphVertexTexture;

    private Graph graph;

    private boolean enableActos;
    private boolean enableGraph;

    public DebugPath(Graph graph, boolean enableActors, boolean enableGraph) {
        this.graph = graph;
        this.enableActos = enableActors;
        this.enableGraph = enableGraph;
        for (String id : Arrays.asList("pacman", "blinky", "inky", "clyde", "pinky")) {
            Texture spriteSheet = new Texture(Gdx.files.internal(Assets.getPathTraceSpriteSheet(id)));
            TextureRegion[][] textureRegions = TextureRegion.split(spriteSheet,
                    spriteSheet.getWidth() / 2,
                    spriteSheet.getHeight());
            actorTextures.put(id, textureRegions[0][1]);
        }
        graphVertexTexture = Assets.TEXTURE_DEBUG_GRAPH_VERTEXES;
    }


    @Override
    public final void draw(Batch batch, float parentAlpha) {
        if (enableGraph){
            for (Vertex vertex : graph.getVertexes()) {
                Point point = (Point) vertex.getId();
                batch.draw(graphVertexTexture, point.x * 8, point.y * 8);
            }
        }
        if (!enableActos || actorPaths.isEmpty()) {
            return;
        }
        for (AliveActor aliveActor : actorPaths.keySet()) {
            if (aliveActor instanceof Pacman){
                continue;
            }
            if (actorPaths.get(aliveActor) == null){
                //Log.warn("pas de path pour %s ??", aliveActor.getId());
                continue;
            }
            for (Vertex vertex : actorPaths.get(aliveActor)) {
                Point point = (Point) vertex.getId();
                batch.draw(actorTextures.get(aliveActor.getId()), point.x * 8, point.y * 8);
            }
        }
    }

    public void setPath(AliveActor actor, LinkedList<Vertex<Point>> path) {
        actorPaths.put(actor, path);
    }

}
