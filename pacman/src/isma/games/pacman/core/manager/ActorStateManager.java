package isma.games.pacman.core.manager;

import com.badlogic.gdx.utils.Array;

import isma.games.TargetHelper;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Fruit;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.actors.Pacman;
import isma.games.pacman.core.tiled.Maze;

import static isma.games.TiledMapHelper.getGridPosition;
import static isma.games.pacman.core.actors.Ghost.GhostState.FRIGTHENED;
import static isma.games.pacman.core.actors.Ghost.GhostState.NAKED;
import static isma.games.pacman.core.actors.Ghost.GhostState.NORMAL;

public class ActorStateManager {

    public void handleState(WorldContainer world) {
        Maze maze = world.getMaze();
        Pacman pacman = world.getPacman();
        Array<Ghost> ghosts = world.getGhosts();

        handleFoodConsuming(world);

        for (int i = 0; i < ghosts.size; i++) {
            Ghost ghost = ghosts.get(i);
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
        Food food = getFoodToEat(world);
        if (food != null) {
            if (food instanceof Dot) {
                handleDotConsuming((Dot) food, world);
            } else if (food instanceof Fruit) {
                handleFruitConsuming((Fruit) food);
            }
        }
    }

    private void handleFruitConsuming(Fruit fruit) {
       fruit.die();
    }

    private void handleDotConsuming(Dot dot, WorldContainer world) {
        dot.die();
        if (dot.isEnergizer()) {
            for (int i = 0; i < world.getGhosts().size; i++) {
                Ghost ghost = world.getGhosts().get(i);
                if (ghost.getState() == NORMAL) {
                    ghost.setFrightened(true);
                }
            }
        }
    }

    public Food getFoodToEat(WorldContainer world) {
        for (Dot dot : world.getRemainingDots()) {
            if (TargetHelper.hit(world.getPacman(), dot)) {
                return dot;
            }
        }
        if (world.getFruit().isAlive()){
        if (TargetHelper.hit(world.getPacman(), world.getFruit())){
            return world.getFruit();
        }
        }
        return null;
    }
}
