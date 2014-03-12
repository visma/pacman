package isma.games.graph;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;


public class Graph<E> {
    private final Array<Vertex<E>> vertexes;
    private final ArrayMap<E, Vertex<E>> map = new ArrayMap<E, Vertex<E>>();

    public Graph(Array<Vertex<E>> argVertexes) {
        vertexes = argVertexes;
        for (Vertex<E> vertex : argVertexes) {
            map.put(vertex.id, vertex);
        }
    }

    public Array<Vertex<E>> getVertexes() {
        return vertexes;
    }

    public Vertex<E> getVertexById(E id) {
        return map.get(id);
    }
}