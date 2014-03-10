package isma.games.pacman.core.manager;

import isma.games.Point;
import isma.games.pacman.core.actors.*;
import isma.games.pacman.core.tiled.Maze;

import java.util.List;

public interface WorldContainer extends WorldEventListener {
    Pacman getPacman();

    Maze getMaze();

    Food getFoodAt(Point position);

    DebugPath getDebugPath();

    List<Ghost> getGhosts();

    Ghost getGhost(String id);

    List<Food> getRemainingFood();

    int getLevel();
}
