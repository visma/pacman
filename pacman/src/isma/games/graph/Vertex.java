package isma.games.graph;

public class Vertex<E> implements Comparable<Vertex<E>> {
    public final E id;
    public Edge<E>[] adjacencies;
    public int minDistance = Integer.MAX_VALUE;
    public Vertex<E> previous;

    public Vertex(E argId) {
        id = argId;
    }

    public int compareTo(Vertex<E> other) {
        return Double.compare(minDistance, other.minDistance);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        if (id != null ? !id.equals(vertex.id) : vertex.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}