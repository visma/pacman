package isma.games.graph;

import isma.games.Point;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GraphBuilderTest {

    //TODO refaire
    /*@Test
    public void build() throws Exception {
        PathMap map = buildPathMap();
        Graph graph = new GraphBuilder().buildGraph(map);

        assertEquals(3, graph.getVertexes().size());
        assertTrue(graph.containsVertex(PointCache.get(0, 0)));
        assertTrue(graph.containsVertex(PointCache.get(0, 1)));
        assertFalse(graph.containsVertex(PointCache.get(1, 0)));
        assertTrue(graph.containsVertex(PointCache.get(1, 1)));

        //Bidirectionnal graph
        assertEquals(2 * 2, graph.getEdges().size());
        assertTrue(graph.containsEdge(PointCache.get(0, 0), PointCache.get(0, 1)));
        assertTrue(graph.containsEdge(PointCache.get(0, 1), PointCache.get(0, 0)));
        assertTrue(graph.containsEdge(PointCache.get(0, 1), PointCache.get(1, 1)));
        assertTrue(graph.containsEdge(PointCache.get(1, 1), PointCache.get(0, 1)));
    }*/

    private PathMap buildPathMap() {
        return new PathMap() {
            @Override
            public int getWidth() {
                return 2;
            }

            @Override
            public int getHeight() {
                return 2;
            }

            @Override
            public boolean isPath(Point position, int pathForce) {
                if (position.x == 0 && position.y == 0) return true;
                if (position.x == 0 && position.y == 1) return true;
                if (position.x == 1 && position.y == 0) return false;
                if (position.x == 1 && position.y == 1) return true;
                return false;
            }
        };
    }
}
