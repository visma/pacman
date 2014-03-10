package isma.games.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private final Set<Vertex> vertexes = new HashSet<Vertex>();
    private final Set<Edge> edges = new HashSet<Edge>();

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes.addAll(vertexes);
        this.edges.addAll(edges);
    }

    public List<Vertex> getVertexes() {
        return new ArrayList<Vertex>(vertexes);
    }

    public List<Edge> getEdges() {
        return new ArrayList<Edge>(edges);
    }


    public boolean containsVertex(Object id) {
        for (Vertex vertex : vertexes) {
            if (vertex.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEdge(Object sourceId, Object targetId) {
        for (Edge edge : edges) {
            if (edge.getSource().getId().equals(sourceId) && edge.getDestination().getId().equals(targetId)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge edge : edges) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}
