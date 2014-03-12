package isma.games.pacman.core.manager;

import com.badlogic.gdx.utils.Array;

import isma.games.Point;
import isma.games.pacman.core.actors.*;
import isma.games.pacman.core.tiled.Maze;

import java.util.List;

public interface WorldContainer extends WorldEventListener {
    Pacman getPacman();

    Maze getMaze();

    Food getFoodAt(Point position);

    DebugPath getDebugPath();

    Array<Ghost> getGhosts();

    Ghost getGhost(String id);

    Array<Food> getRemainingFood();

    int getLevel();
}
