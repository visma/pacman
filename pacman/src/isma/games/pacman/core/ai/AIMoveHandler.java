package isma.games.pacman.core.ai;

import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.List;

import isma.games.Direction;
import isma.games.Log;
import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.graph.Dijkstra;
import isma.games.graph.Graph;
import isma.games.graph.GraphBuilder;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.manager.DefaultMoveHandler;
import isma.games.pacman.core.manager.MoveHandlerHelper;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Log.debug;
import static isma.games.Log.info;
import static isma.games.Log.start;
import static isma.games.Log.warn;
import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.utils.TargetUtils.stringify;

public abstract class AIMoveHandler extends DefaultMoveHandler {
    protected final WorldContainer world;
    protected final AliveActor aiActor;
    protected final Maze maze;

    protected Dijkstra<Point> dijkstra;
    protected final GraphBuilder graphBuilder;
    protected Graph<Point> graph;


    private Direction currentDirection;

    protected AIMoveHandler(AliveActor actor, WorldContainer world, GraphBuilder graphBuilder) {
        this.world = world;
        this.graphBuilder = graphBuilder;
        aiActor = actor;
        maze = world.getMaze();

        graph = graphBuilder.buildGraph(maze);
        dijkstra = new Dijkstra<Point>(graph);
    }

    @Override
    public void initDirection(float remainingLen) {
        start(Log.LOG_DEBUG);
        if (nextMoveChangeTurnTile(remainingLen)) {
            Direction aiDirection = computeDirection(maze, remainingLen);
//            debug("dijkstra direction : %s", aiDirection);
            setDirection(aiDirection);
        } else {
//            debug("no dijkstra");
        }
    }

    protected boolean nextMoveChangeTurnTile(float remainingLen) {
        Target nextCenterPosition = MoveHandlerHelper.nextCenterPosition(maze, aiActor, remainingLen);
        Point tilePosition = getGridPosition(maze, aiActor);
        Point nextTilePosition = getGridPosition(maze, nextCenterPosition);
//        debug("remainingLen=%s", remainingLen);
//        debug("actor.currentDirection=%s", aiActor.getCurrentDirection());
//        debug("tilePos=%s, nextTilePos=%s", tilePosition, nextTilePosition);
        int turns = 0;
        //TODO manque handleOutOfBounds : pas tres important visiblement...
        boolean left = maze.isPath(nextTilePosition.onLeft(), getPathForce());
        boolean right = maze.isPath(nextTilePosition.onRight(), getPathForce());
        boolean south = maze.isPath(nextTilePosition.onBottom(), getPathForce());
        boolean north = maze.isPath(nextTilePosition.onTop(), getPathForce());
        turns += left ? 1 : 0;
        turns += right ? 1 : 0;
        turns += south ? 1 : 0;
        turns += north ? 1 : 0;
//        debug("turns=%s", turns);
        if (turns > 2) {
            return true;
        }
        if (turns == 2) {
            //check corners
            return !(left && right || north && south);
        }
        if (turns == 1) {
            //corner passed
            return true;
        }
        return false;
    }

    private Direction computeDirection(Maze maze, float remainingLen) {
//        warn("------- dijkstra execution : %s", aiActor.getId());
//        info("actor=%s, atTile=%s", stringify(aiActor), getGridPosition(maze, aiActor));
        Target target = searchTarget();
//        warn("target=%s, atTile=%s", stringify(target), getGridPosition(maze, target));
        Point tilePosition = getGridPosition(maze, aiActor);

        Point targetTile = TiledMapHelper.getGridPosition(maze, target);

        Array<Vertex<Point>> path = dijkstra.getShortestPathTo(tilePosition, targetTile);
        world.getDebugPath().setPath(aiActor, path);
        if (path == null) {
//            warn("no path for " + aiActor.getId());
            return aiActor.getCurrentDirection();
        }
        if (path.size < 2) {
            //TODO pk j'ai mis ca ?;
        }
//        warn("%s position: %s", aiActor.getId(), tilePosition);
//        warn("path length : " + path.size());
//        warn("path : " + path);
        Direction aiDirection = PathFinding.nextDirection(path, maze, aiActor, remainingLen);
//        warn("direction : " + aiDirection);
        return aiDirection;
    }

    protected abstract Target searchTarget();

    @Override
    public Direction getDirection() {
        return currentDirection;
    }

    @Override
    public void setDirection(Direction direction) {
        currentDirection = direction;
    }
}
