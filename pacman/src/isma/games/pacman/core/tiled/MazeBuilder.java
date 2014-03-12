package isma.games.pacman.core.tiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Point;
import isma.games.PointCache;
import isma.games.TiledMapHelper;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Fruit;

public class MazeBuilder {
    private MazeBuilder() {
    }

    public static ArrayMap<Point, Food> loadFood(Maze maze) {
        ArrayMap<Point, Food> foodMap = new ArrayMap<Point, Food>();
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                Point gridPosition = PointCache.get(i, j);
                boolean isEnergizer = maze.isEnergizer(gridPosition);
                if (maze.isDot(gridPosition) || isEnergizer) {
                    putDot(foodMap, maze, gridPosition, isEnergizer);
                }
            }
        }
        putFruit(foodMap, maze, maze.getFruitPosition());
        return foodMap;
    }

    private static void putFruit(ArrayMap<Point, Food> foodMap, Maze maze, Point foodPosition) {
        putFood(foodMap, maze, foodPosition, new Fruit());
    }

    private static void putDot(ArrayMap<Point, Food> foodMap, Maze maze, Point foodPosition, boolean energizer) {
        putFood(foodMap, maze, foodPosition, new Dot(energizer));
    }

    private static void putFood(ArrayMap<Point, Food> foodMap, Maze maze, Point foodPosition, Food food) {
        Vector2 position = TiledMapHelper.getPosition(maze, foodPosition);
        food.setPosition(position.x, position.y);
        //trace("create food [%s ]at tile{%s}, position{x=%s, y=%s}", food, food.getClass(), food, food.getX(), food.getY());
        foodMap.put(foodPosition, food);
    }


}
