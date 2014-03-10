package isma.games.graph;

public class Edge {
    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private int weight;


    public Edge(Vertex source, Vertex destination, int weight) {
        this(source.getName() + " - " + destination.getName(), source, destination, weight);
    }

    public Edge(String id, Vertex source, Vertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return source + "->" + destination + " : " + weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        if (destination != null ? !destination.equals(edge.destination) : edge.destination != null) return false;
        if (id != null ? !id.equals(edge.id) : edge.id != null) return false;
        if (source != null ? !source.equals(edge.source) : edge.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}