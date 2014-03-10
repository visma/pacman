package isma.games.pacman.core.ai;

import isma.games.*;
import isma.games.graph.Dijkstra;
import isma.games.graph.Graph;
import isma.games.graph.GraphBuilder;
import isma.games.graph.Vertex;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.manager.DefaultMoveHandler;
import isma.games.pacman.core.manager.MoveHandlerHelper;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import java.util.LinkedList;

import static isma.games.Log.*;
import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.utils.TargetUtils.stringify;

public abstract class AIMoveHandler extends DefaultMoveHandler {
    protected final GraphBuilder graphBuilder;
    protected final WorldContainer world;
    protected final AliveActor aiActor;
    protected final Maze maze;

    protected Graph graph;
    private Point tilePosition;

    private Direction currentDirection;

    protected AIMoveHandler(AliveActor actor, WorldContainer world, GraphBuilder graphBuilder) {
        this.world = world;
        this.graphBuilder = graphBuilder;
        aiActor = actor;
        maze = world.getMaze();
        graph = graphBuilder.buildGraph(maze);
        tilePosition = getGridPosition(maze, aiActor);
    }

    @Override
    public void initDirection(float remainingLen) {
        start(Log.LOG_DEBUG);
        if (nextMoveChangeTurnTile(remainingLen)) {
            Direction aiDirection = computeDirection(maze, remainingLen);
            debug("dijkstra direction : %s", aiDirection);
            setDirection(aiDirection);
        } else {
            debug("no dijkstra");
        }
    }

    protected boolean nextMoveChangeTurnTile(float remainingLen) {
        Target nextCenterPosition = MoveHandlerHelper.nextCenterPosition(maze, aiActor, remainingLen);
        Point tilePosition = getGridPosition(maze, aiActor);
        Point nextTilePosition = getGridPosition(maze, nextCenterPosition);
        debug("remainingLen=%s", remainingLen);
        debug("actor.currentDirection=%s", aiActor.getCurrentDirection());
        debug("tilePos=%s, nextTilePos=%s", tilePosition, nextTilePosition);
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
        debug("turns=%s", turns);
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
        warn("------- dijkstra execution : %s", aiActor.getId());
        info("actor=%s, atTile=%s", stringify(aiActor), getGridPosition(maze, aiActor));
        Target target = searchTarget();
        info("target=%s, atTile=%s", stringify(target), getGridPosition(maze, target));
        tilePosition = getGridPosition(maze, aiActor);

        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.execute(new Vertex<Point>(tilePosition));

        Point targetTile = TiledMapHelper.getGridPosition(maze, target);
        LinkedList<Vertex<Point>> path = dijkstra.getPath(new Vertex<Point>(targetTile));
        world.getDebugPath().setPath(aiActor, path);
        if (path == null) {
            warn("no path for " + aiActor.getId());
            return aiActor.getCurrentDirection();
        }
        if (path.isEmpty() || path.size() == 1) {
            //TODO pk j'ai mis ca ?;
        }
        warn("%s position: %s", aiActor.getId(), tilePosition);
        warn("path length : " + path.size());
        warn("path : " + path);
        Direction aiDirection = PathFinding.nextDirection(path, maze, aiActor, remainingLen);
        warn("direction : " + aiDirection);
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
