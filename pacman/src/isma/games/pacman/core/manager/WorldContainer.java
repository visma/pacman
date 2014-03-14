package isma.games.pacman.core.manager;

import com.badlogic.gdx.utils.Array;

import isma.games.Point;
import isma.games.pacman.core.actors.DebugPath;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.actors.WorldEventListener;
import isma.games.pacman.core.tiled.Maze;

public interface WorldContainer extends WorldEventListener {
    Maze getMaze();

    DebugPath getDebugPath();

    Pacman getPacman();

    Array<Ghost> getGhosts();

    Dot getDotAt(Point position);

    Ghost getGhost(String id);

    Array<Dot> getRemainingDots();

    Food getFruit();

    int getLevel();

}
