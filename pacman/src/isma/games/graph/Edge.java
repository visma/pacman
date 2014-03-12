package isma.games.graph;

public class Edge<E> {
    public final Vertex<E> target;
    public final int weight;

    public Edge(Vertex<E> argTarget, int argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}
