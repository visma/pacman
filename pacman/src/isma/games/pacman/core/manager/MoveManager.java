package isma.games.pacman.core.manager;

import isma.games.Log;
import isma.games.NumberHelper;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;
import isma.games.pacman.core.tiled.MazeMoveHelper;

import java.util.HashMap;
import java.util.Map;

import static isma.games.Log.*;
import static isma.games.utils.TargetUtils.stringify;

public class MoveManager {
    public static final int FPS_LIMIT_CAP = 60;
    private static final int FPS_OPTIMUM = 60;

    private static final float ROUND_DELTA = 0.000001f;
    private Map<AliveActor, MoveHandler> map = new HashMap<AliveActor, MoveHandler>();

    public MoveManager() {
    }

    public void addHandler(AliveActor actor, MoveHandler moveHandler) {
        moveHandler.setDirection(actor.getCurrentDirection());
        map.put(actor, moveHandler);
    }

    public void moveAll(float delta, Maze maze) {
        for (AliveActor actor : map.keySet()) {
            MoveHandler moveHandler = map.get(actor);
            info("before (%s): actor=%s, tile=%s, directions{CURR=%s, INPUT=%s}",
                    actor.getId(),
                    stringify(actor),
                    TiledMapHelper.getGridPosition(maze, actor),
                    actor.getCurrentDirection(), moveHandler.getDirection());
            moveActor(delta, maze, actor, moveHandler);
            info("after (%s): actor=%s, tile=%s, directions{CURR=%s, INPUT=%s}",
                    actor.getId(),
                    stringify(actor),
                    TiledMapHelper.getGridPosition(maze, actor),
                    actor.getCurrentDirection(), moveHandler.getDirection());
        }
    }

    private boolean moveOnNewDirection(Maze maze,
                                       MoveHandler moveHandler,
                                       AliveActor actor,
                                       float distance) {
        float requestedPathLen = MazeMoveHelper.getPathLength(maze, actor.getCenter(),
                moveHandler.getDirection(),
                moveHandler.getPathForce());
        trace("chemin direct(%s) : %s", moveHandler.getDirection(), requestedPathLen);
        if (requestedPathLen > 0) {
            actor.setCurrentDirection(moveHandler.getDirection());
            if (distance > requestedPathLen) {
                distance = requestedPathLen;
            }
            trace("(input) move : " + distance);
            moveHandler.move(actor, maze, distance);
            return true;
        }
        return false;
    }

    private void moveStraightThenTurn(Maze maze, MoveHandler moveHandler, AliveActor actor,
                                         float currentPathLen, float remainingLen){
        remainingLen = remainingLen - currentPathLen;
        trace("turn : %s=%s then %s=%s", actor.getCurrentDirection(), currentPathLen, moveHandler.getDirection(), remainingLen);

        trace("1=> default-move %s : %s", actor.getCurrentDirection(), currentPathLen);
        moveHandler.move(actor, maze, currentPathLen);
        actor.setCurrentDirection(moveHandler.getDirection());

        actor.setX(NumberHelper.roundToNearestInteger(actor.getX(), ROUND_DELTA));
        actor.setY(NumberHelper.roundToNearestInteger(actor.getY(), ROUND_DELTA));
        debug("correct-float-precision-on-position=> %s", stringify(actor));


        trace("before change of direction - {current=%s, input=%s}", actor.getCurrentDirection(), moveHandler.getDirection());
        trace("before change of direction - center : " + actor.getCenter());
        trace("2=> input-move : " + remainingLen);
        moveHandler.move(actor, maze, remainingLen);
    }

    private void moveActor(float delta, Maze maze, AliveActor actor, MoveHandler moveHandler) {
        start(Log.LOG_DEBUG);
        float remainingLen = actor.getSpeed() * getFps() * (delta > 1f / getFps() ? delta : 1f / getFps());
        debug(" - remaining distance : " + remainingLen);

        moveHandler.initDirection(remainingLen);
        /******************************************
         * MOVE POSSIBLE WITH NEXT DIRECTION
         ******************************************/

        if (moveOnNewDirection(maze, moveHandler, actor, remainingLen)) {
            return;
        }

        float pathLenBeforeTurn = MazeMoveHelper.getPathLengthBeforeDirection(
                maze,
                actor.getCenter(),
                actor.getCurrentDirection(),
                moveHandler.getDirection(),
                moveHandler.getPathForce());
        trace("distance before turn : %s", pathLenBeforeTurn);
        if (pathLenBeforeTurn == 0 || remainingLen <= pathLenBeforeTurn) {
            float currentPathLen = MazeMoveHelper.getPathLength(
                    maze,
                    actor.getCenter(),
                    actor.getCurrentDirection(),
                    moveHandler.getPathForce());
            remainingLen = remainingLen > currentPathLen ? currentPathLen : remainingLen;
            trace("default move : " + remainingLen);
            moveHandler.move(actor, maze, remainingLen);
            return;
        }
        moveStraightThenTurn(maze, moveHandler, actor, pathLenBeforeTurn, remainingLen);
    }


    public MoveHandler getHandler(AliveActor actor) {
        return map.get(actor);
    }

    public int getFps() {
        return FPS_OPTIMUM;
    }

}
