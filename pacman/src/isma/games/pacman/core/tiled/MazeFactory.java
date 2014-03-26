package isma.games.pacman.core.tiled;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import isma.games.pacman.core.assets.Assets;

import static isma.games.pacman.core.assets.Assets.PACMAN_TILEDMAP_FILEPATH;

public class MazeFactory {
    private MazeFactory() {
    }

    public static Maze buildMaze(OrthographicCamera camera) {
        return new Maze(new TmxMapLoader().load(PACMAN_TILEDMAP_FILEPATH), Assets.MAIN_LAYER, camera);
    }
}
