package isma.games.pacman.core.ai;

import com.badlogic.gdx.utils.Array;

import isma.games.Direction;
import isma.games.Log;
import isma.games.Point;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.graph.Dijkstra;
import isma.games.graph.Graph;
import isma.games.graph.Vertex;
import isma.games.pacman.core.GraphBuilder;
import isma.games.pacman.core.actors.ActorLog;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.manager.DefaultMoveHandler;
import isma.games.pacman.core.manager.MoveHandlerHelper;
import isma.games.pacman.core.manager.WorldContainer;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Log.start;
import static isma.games.TiledMapHelper.getGridPosition;

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
//        start(Log.LOG_DEBUG);
//        ActorLog.logIfPinky(aiActor, "pinky direction = " + aiActor.getCurrentDirection());
        if (nextMoveChangeTurnTile(remainingLen)) {
            //System.out.println("calcul AI at " + aiActor.getX() + " - " + aiActor.getY());
            Direction aiDirection = computeDirection(maze, remainingLen);
//            debug("dijkstra direction : %s", aiDirection);
            setDirection(aiDirection);
        } else {
//            debug("no dijkstra");
        }
    }


    protected boolean nextMoveChangeTurnTile(float remainingLen) {
        Target nextCenterPosition = MoveHandlerHelper.nextCenterPosition(maze, aiActor, remainingLen);
        if (!MoveHandlerHelper.crossTile(maze, aiActor, nextCenterPosition)) {
//            ActorLog.logIfPinky(aiActor, "not crossTile : " + getGridPosition(maze, aiActor));
            return false;
        }
        Point nextTilePosition = getGridPosition(maze, nextCenterPosition);
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
        if (turns > 2) {
            return true;
        }
        if (turns == 2) {
            //check corners
            boolean corner = !(left && right || north && south);
            return corner;
        }
        if (turns == 1) {
            return true;
        }
        return false;
    }

    private Direction computeDirection(Maze maze, float remainingLen) {
        Target target = searchTarget();
        Point tilePosition = getGridPosition(maze, aiActor);

        Point targetTile = TiledMapHelper.getGridPosition(maze, target);
        Array<Vertex<Point>> path = dijkstra.getShortestPathTo(tilePosition, targetTile);
        world.getDebugPath().setPath(aiActor, path);
        if (path == null) {
            return aiActor.getCurrentDirection();
        }
        Direction aiDirection = PathFinding.nextDirection(path, maze, aiActor, remainingLen);
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
