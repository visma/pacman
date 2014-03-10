package isma.games.graph;

import isma.games.Point;

public interface PathMap {
    int getWidth();

    int getHeight();

    boolean isPath(Point position, int pathForce);
}
