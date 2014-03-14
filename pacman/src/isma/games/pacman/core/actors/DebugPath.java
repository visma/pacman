package isma.games.pacman.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Arrays;

import isma.games.Point;
import isma.games.graph.Graph;
import isma.games.graph.Vertex;
import isma.games.pacman.core.assets.Assets;

public class DebugPath extends Actor {
    private final ArrayMap<String, TextureRegion> actorTextures = new ArrayMap<String, TextureRegion>();
    private final ArrayMap<AliveActor, Array<Vertex<Point>>> actorPaths = new ArrayMap<AliveActor, Array<Vertex<Point>>>();
    private final Texture graphVertexTexture;

    private Graph<Point> graph;

    private boolean enableGhost;
    private boolean enablePacman;
    private boolean enableGraph;

    public DebugPath(Graph graph, boolean enableGhost, boolean enablePacman, boolean enableGraph) {
        this.graph = graph;
        this.enableGhost = enableGhost;
        this.enablePacman = enablePacman;
        this.enableGraph = enableGraph;
        for (String id : Arrays.asList("pacman", "blinky", "inky", "clyde", "pinky")) {
            //TODO pas de new Texture ici !!!
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
        if (enableGraph) {
            for (Vertex vertex : graph.getVertexes()) {
                Point point = (Point) vertex.id;
                batch.draw(graphVertexTexture, point.x * 8, point.y * 8);
            }
        }
        if (actorPaths.size == 0) {
            return;
        }
        for (AliveActor aliveActor : actorPaths.keys()) {
            if (actorPaths.get(aliveActor) == null) {
                //Log.warn("pas de path pour %s ??", aliveActor.getId());
                continue;
            }
            for (Vertex vertex : actorPaths.get(aliveActor)) {
                if (aliveActor instanceof Ghost && !enableGhost) {
                    continue;
                } else if (aliveActor instanceof Pacman && !enablePacman) {
                    continue;
                }
                Point point = (Point) vertex.id;
                batch.draw(actorTextures.get(aliveActor.getId()), point.x * 8, point.y * 8);
            }
        }
    }

    public void setPath(AliveActor actor, Array<Vertex<Point>> path) {
        actorPaths.put(actor, path);
    }

}
