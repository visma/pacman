package isma.games.pacman.core.manager;

import isma.games.TargetHelper;
import isma.games.pacman.core.actors.*;
import isma.games.pacman.core.tiled.Maze;

import java.util.List;

import static isma.games.Log.*;
import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.pacman.core.actors.Ghost.GhostState.*;

public class ActorStateManager {

    public void handleState(WorldContainer world) {
        Maze maze = world.getMaze();
        Pacman pacman = world.getPacman();
        List<Ghost> ghosts = world.getGhosts();

        handleFoodConsuming(world);

        for (Ghost ghost : ghosts) {
            boolean hitPacman = TargetHelper.hit(ghost, pacman);
            if (hitPacman && ghost.getState() != NAKED) {
                if (ghost.getState() == FRIGTHENED) {
                    ghost.die();
                } else if (ghost.getState() == NORMAL) {
                    pacman.die();
                }
            }
            boolean hitHouse = TargetHelper.hit(getGridPosition(maze, ghost), maze.getGhostHouse());
            if (hitHouse && ghost.getState() == NAKED) {
                ghost.revive();
            }
        }
    }

    private void handleFoodConsuming(WorldContainer world) {
        Maze maze = world.getMaze();
        Food food = getFoodToEat(world);
        if (food != null) {
            trace("miam : " + getGridPosition(maze, food));
            if (food instanceof Dot) {
                handleDotConsuming((Dot) food, world);
            } else if (food instanceof Fruit) {
                handleFruitConsuming((Fruit) food, world);
            }
        }
    }

    private void handleFruitConsuming(Fruit fruit, WorldContainer stage) {
        error("not implemented");
    }

    private void handleDotConsuming(Dot dot, WorldContainer stage) {
        dot.die();
        if (dot.isEnergizer()) {
            for (Ghost ghost : stage.getGhosts()) {
                if (ghost.getState() == NORMAL) {
                    ghost.setFrightened(true);
                }
            }
        }
    }

    public Food getFoodToEat(WorldContainer stage) {
        for (Food food : stage.getRemainingFood()) {
            if (TargetHelper.hit(stage.getPacman(), food)) {
                return food;
            }
        }
        return null;
    }
}
