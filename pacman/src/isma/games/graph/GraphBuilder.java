package isma.games.graph;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Point;

public class GraphBuilder {
    private static final int PATH_FORCE = 2;

    public Graph<Point> buildGraph(PathMap pathMap) {
        ArrayMap<Point, Vertex<Point>> vertexes = buildVertexes(pathMap);
        buildEdges(pathMap, vertexes);
        return new Graph<Point>(vertexes.values().toArray());
    }

    private void buildEdges(PathMap pathMap, ArrayMap<Point, Vertex<Point>> vertexes) {
        for (Point sourcePoint : vertexes.keys()) {
            Vertex<Point> vertex = vertexes.get(sourcePoint);
            Array<Point> adjacentPoints = getAdjacentPaths(pathMap, sourcePoint);
            vertex.adjacencies = new Edge[adjacentPoints.size];

            for (int i = 0; i < adjacentPoints.size; i++) {
                Point adjacentPoint = adjacentPoints.get(i);
                int weight = getWeight(vertexes, adjacentPoint);
                vertex.adjacencies[i] = new Edge<Point>(vertexes.get(adjacentPoint), weight);
            }
        }
    }


    protected int getWeight(ArrayMap<Point, Vertex<Point>> vertexes, Point destination) {
        return 1;
    }

    private ArrayMap<Point, Vertex<Point>> buildVertexes(PathMap pathMap) {
        ArrayMap<Point, Vertex<Point>> vertexes = new ArrayMap<Point, Vertex<Point>>();
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

    private Array<Point> getAdjacentPaths(PathMap pathMap, Point point) {
        int xLowerBound = 0;
        int xUpperBound = pathMap.getWidth() - 1;

        int yLowerBound = 0;
        int yUpperBound = pathMap.getHeight() - 1;

        Array<Point> adjacents = new Array<Point>();
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


}

