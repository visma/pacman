package isma.games;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import isma.games.pacman.core.manager.MoveManager;

public class PacmanDesktop {
	/*public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "pacman";
		//cfg.useGL20 = false;
		cfg.width = 224;
		cfg.height = 288;
        cfg.
		
		new LwjglApplication(new PacmanGame(), cfg);
	}*/

    public static void main (String[] args) {
        int fps = MoveManager.FPS_LIMIT_CAP;
        int scaleRatio = 3;
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.useGL20 = true;
        config.width = 224 * scaleRatio;
        config.height = 288 * scaleRatio;

        config.vSyncEnabled = false;
        config.foregroundFPS = fps;
        config.backgroundFPS = fps;
        new LwjglApplication(new PacmanGame(), config);
    }
}
