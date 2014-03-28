package isma.games.pacman.core.tiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Point;
import isma.games.PointCache;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Fruit;

public class MazeBuilder {
    private MazeBuilder() {
    }

    public static ArrayMap<Point, Dot> loadDots(Maze maze) {
        ArrayMap<Point, Dot> foodMap = new ArrayMap<Point, Dot>();
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                Point gridPosition = PointCache.get(i, j);
                boolean isEnergizer = maze.isEnergizer(gridPosition);
                if (maze.isDot(gridPosition) || isEnergizer) {
                    putDot(foodMap, maze, gridPosition, isEnergizer);
                }
            }
        }
        return foodMap;
    }

    public static Fruit loadFruit(Maze maze) {
        Fruit fruit = new Fruit();
        Vector2 position = TiledMapHelper.getPosition(maze, maze.getFruitPosition());
        fruit.setPosition(position.x - 4, position.y - 4);
        return fruit;
    }

    private static void putDot(ArrayMap<Point, Dot> foodMap, Maze maze, Point foodPosition, boolean energizer) {
        Vector2 position = TiledMapHelper.getPosition(maze, foodPosition);
        Dot food = new Dot(energizer, position.x, position.y);
        //trace("create food [%s ]at tile{%s}, position{x=%s, y=%s}", food, food.getClass(), food, food.getX(), food.getY());
        foodMap.put(foodPosition, food);
    }


}
