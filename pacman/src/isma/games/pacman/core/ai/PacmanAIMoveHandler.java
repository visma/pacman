package isma.games.pacman.core.ai;

import isma.games.Point;
import isma.games.PointCache;
import isma.games.Target;
import isma.games.TargetHelper;
import isma.games.graph.Dijkstra;
import isma.games.pacman.core.actors.Food;
import isma.games.pacman.core.actors.Ghost;
import isma.games.pacman.core.manager.WorldContainer;

import static isma.games.Log.info;

public class PacmanAIMoveHandler extends AIMoveHandler {
    public PacmanAIMoveHandler(WorldContainer stage) {
        super(stage.getPacman(), stage, new PacmanAIGraphBuilder(stage));
    }

    @Override
    protected boolean nextMoveChangeTurnTile(float remainingLen) {
        return true;
        //return super.nextMoveChangeTurnTile(remainingLen);
    }

    @Override
    public int getPathForce() {
        return 1;
    }

    protected Target searchTarget() {
        graph = graphBuilder.buildGraph(maze);
        dijkstra = new Dijkstra<Point>(graph);

        Target farestFoodFromGhosts = world.getRemainingFood().get(0);
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                Food food = world.getFoodAt(PointCache.get(i, j));
                if (food == null){
                    continue;
                }
                farestFoodFromGhosts = getFarestFoodFromGhosts(farestFoodFromGhosts, food);
            }
        }
//        info("farest food : " + farestFoodFromGhosts);
        return farestFoodFromGhosts;
    }


    private Target getFarestFoodFromGhosts(Target oldFarestFood, Food food) {
        //Hack pour test sans fantomes
        /*if (stage.getGhosts().isEmpty()) {
            for (int i = 0; i < maze.getWidth(); i++) {
                for (int j = 0; j < maze.getHeight(); j++) {
                    Dot dotAt = stage.getDotAt(PointCache.get(i, j));
                    if (dotAt != null && dotAt.isAlive()) {
                        if (i == 1 && j == 3) {
                            System.out.println("la");
                        }
                        return dotAt;
                    }
                }
            }
        }*/
        if (oldFarestFood == null) {
            return food;
        }
        if (food == null || !food.isAlive()) {
            return oldFarestFood;
        }

        for (Ghost ghost : world.getGhosts()) {
            if (TargetHelper.dst(food, ghost) > TargetHelper.dst(oldFarestFood, ghost)) {
                return food;
            }
        }
        return oldFarestFood;
    }
}
