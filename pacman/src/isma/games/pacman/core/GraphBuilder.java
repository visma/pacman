package isma.games.pacman.core;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Point;
import isma.games.PointCache;
import isma.games.graph.Edge;
import isma.games.graph.Graph;
import isma.games.graph.PathMap;
import isma.games.graph.Vertex;

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
                if (pathMap.isPath(PointCache.get(i, j), PATH_FORCE)) {
                    Point point = PointCache.get(i, j);
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
            left = PointCache.get(point.x - 1, point.y);
        } else if (point.x == xLowerBound) {
            left = PointCache.get(xUpperBound, point.y);
        } else {
            throw new RuntimeException("?");
        }
        if (point.x < xUpperBound) {
            right = PointCache.get(point.x + 1, point.y);
        } else if (point.x == xUpperBound) {
            right = PointCache.get(xLowerBound, point.y);
        } else {
            throw new RuntimeException("?");
        }
        if (point.y > yLowerBound) {
            up = PointCache.get(point.x, point.y - 1);
        } else if (point.x == xLowerBound) {
            up = PointCache.get(point.x, yUpperBound);
        } else {
            throw new RuntimeException("?");
        }
        if (point.y < yUpperBound) {
            down = PointCache.get(point.x, point.y + 1);
        } else if (point.y == yUpperBound) {
            down = PointCache.get(point.x, yLowerBound);
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

