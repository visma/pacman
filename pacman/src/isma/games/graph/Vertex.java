package isma.games.graph;

public class Vertex<E> {
    final private E id;
    final private String name;


    public Vertex(E id) {
        this(id, id.toString());
    }

    public Vertex(E id, String name) {
        this.id = id;
        this.name = name;
    }

    public E getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        if (!id.equals(vertex.id)) return false;

        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}