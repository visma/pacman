package isma.games.pacman.core.manager;

import com.badlogic.gdx.math.Rectangle;

import isma.games.Direction;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.Log.debug;
import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.utils.TargetUtils.stringify;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class DefaultMoveHandler implements MoveHandler {
    private Direction direction;

    boolean canMove(AliveActor actor, Maze maze, Direction direction, float offset) {
        Rectangle futureCenter = new Rectangle(actor.getCenter());
        switch (direction) {
            case EAST:
                futureCenter.x += offset;
                futureCenter.x = (float) ceil(futureCenter.x);
                break;
            case SOUTH:
                futureCenter.y -= offset;
                futureCenter.y = (float) floor(futureCenter.y);
                break;
            case WEST:
                futureCenter.x -= offset;
                futureCenter.x = (float) floor(futureCenter.x);
                break;
            case NORTH:
                futureCenter.y += offset;
                futureCenter.y = (float) ceil(futureCenter.y);
                break;
        }
        TiledMapHelper.handleOutOfBounds(maze, futureCenter);
        return maze.isPath(getGridPosition(maze, futureCenter.x, futureCenter.y), getPathForce());
    }

    @Override
    public int getPathForce() {
        return 1;
    }

    public void move(AliveActor actor, Maze maze, float offset) {
//        info("move(actor=%s, offset=%s, direction=%s)", stringify(actor), offset, actor.getCurrentDirection());
        actor.setStopped(offset == 0f);
        if (!canMove(actor, maze, actor.getCurrentDirection(), offset)) {
            debug("can't move actor=%s toward %s with value : %s", stringify(actor), actor.getCurrentDirection(), offset);
            return;
        }
        switch (actor.getCurrentDirection()) {
            case NORTH:
                actor.setY(actor.getY() + offset);
                break;
            case SOUTH:
                actor.setY(actor.getY() - offset);
                break;
            case WEST:
                actor.setX(actor.getX() - offset);
                break;
            case EAST:
                actor.setX(actor.getX() + offset);
                break;
        }
//        info("end (before rebound): actor=%s", stringify(actor));
        TiledMapHelper.handleOutOfBounds(maze, actor);
//        info("end : actor=%s", stringify(actor));
    }

    @Override
    public void initDirection(float remainingLen) {
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public Direction getDirection() {
        return direction;
    }


}
