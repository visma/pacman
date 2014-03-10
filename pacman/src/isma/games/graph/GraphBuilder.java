package isma.games.graph;


import isma.games.Log;
import isma.games.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {
    private static final int PATH_FORCE = 2;

    public Graph buildGraph(PathMap pathMap) {
        Map<Point, Vertex> vertexes = buildVertexes(pathMap);
        List<Edge> edges = buildEdges(pathMap, vertexes);
        return new Graph(new ArrayList<Vertex>(vertexes.values()), edges);
    }

    private List<Edge> buildEdges(PathMap pathMap, Map<Point, Vertex> vertexes) {
        List<Edge> edges = new ArrayList<Edge>();

        for (Point point : vertexes.keySet()) {
            Vertex source = vertexes.get(point);
            List<Point> adjacentPoints = getAdjacentPaths(pathMap, point);
            for (Point adjacentPoint : adjacentPoints) {
                Vertex destination = vertexes.get(adjacentPoint);
                edges.add(new Edge(source, destination, getWeight(vertexes, source, destination)));
            }
        }
        return edges;
    }

    protected int getWeight(Map<Point, Vertex> vertexes, Vertex<Point> source, Vertex<Point> destination) {
        return 1;
    }

    private Map<Point, Vertex> buildVertexes(PathMap pathMap) {
        Map<Point, Vertex> vertexes = new HashMap<Point, Vertex>();
        for (int i = 0; i < pathMap.getWidth(); i++) {
            for (int j = 0; j < pathMap.getHeight(); j++) {
                if (pathMap.isPath(new Point(i, j), PATH_FORCE)) {
                    Point point = new Point(i, j);
                    vertexes.put(point, new Vertex<Point>(point));
                }
            }
        }
        return vertexes;
    }

    private List<Point> getAdjacentPaths(PathMap pathMap, Point point) {
        int xLowerBound = 0;
        int xUpperBound = pathMap.getWidth() - 1;

        int yLowerBound = 0;
        int yUpperBound = pathMap.getHeight() - 1;

        List<Point> adjacents = new ArrayList<Point>();
        Point left, right, up, down;

        if (point.x > xLowerBound) {
            left = new Point(point.x - 1, point.y);
        } else if (point.x == xLowerBound) {
            left = new Point(xUpperBound, point.y);
        } else {
            throw new RuntimeException("?");
        }
        if (point.x < xUpperBound) {
            right = new Point(point.x + 1, point.y);
        } else if (point.x == xUpperBound) {
            right = new Point(xLowerBound, point.y);
        } else {
            throw new RuntimeException("?");
        }
        if (point.y > yLowerBound) {
            up = new Point(point.x, point.y - 1);
        } else if (point.x == xLowerBound) {
            up = new Point(point.x, yUpperBound);
        } else {
            throw new RuntimeException("?");
        }
        if (point.y < yUpperBound) {
            down = new Point(point.x, point.y + 1);
        } else if (point.y == yUpperBound) {
            down = new Point(point.x, yLowerBound);
        } else {
            throw new RuntimeException("?");
        }

        if (pathMap.isPath(left, PATH_FORCE)) {
            adjacents.add(left);
        }
        if (pathMap.isPath(right, PATH_FORCE)) {
            adjacents.add(right);
        }
        if (pathMap.isPath(down, PATH_FORCE)) {
            adjacents.add(down);
        }
        if (pathMap.isPath(up, PATH_FORCE)) {
            adjacents.add(up);
        }

        return adjacents;
    }

    public void printPathWeight(List<Vertex<Point>> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            int weight = getWeight(null, path.get(i), path.get(i + 1));
            if (weight > 1000){
                Log.info(" - weight=%s, a=%s, b=%s", weight, path.get(i), path.get(i + 1));
            }
        }
    }

}

