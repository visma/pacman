package isma.games.pacman.core.manager;

import isma.games.Direction;
import isma.games.pacman.core.actors.AliveActor;
import isma.games.pacman.core.tiled.Maze;

public interface MoveHandler {
    int getPathForce();

    void move(AliveActor actor, Maze maze, float offset);

    Direction getDirection();

    void setDirection(Direction direction);

    void initDirection(float remainingLen);
}
