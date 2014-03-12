package isma.games.graph;

import com.badlogic.gdx.utils.Array;

import java.util.PriorityQueue;

public class Dijkstra<E> {
    private Graph<E> graph;

    public Dijkstra(Graph<E> graph) {
        this.graph = graph;
    }

    public void computePaths(Vertex<E> source) {
        clear();
        source.minDistance = 0;
        PriorityQueue<Vertex<E>> vertexQueue = new PriorityQueue<Vertex<E>>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex<E> u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex<E> v = e.target;
                int weight = e.weight;
                int distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    private void clear() {
        for (Vertex<E> pointVertex : graph.getVertexes()) {
            pointVertex.minDistance = Integer.MAX_VALUE;
            pointVertex.previous = null;
        }

    }

    public Array<Vertex<E>> getShortestPathTo(E source, E target) {
        computePaths(graph.getVertexById(source));
        Vertex<E> targetVertex = graph.getVertexById(target);
        Array<Vertex<E>> path = new Array<Vertex<E>>();
        for (Vertex<E> vertex = targetVertex; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        path.reverse();
        return path;
    }
}