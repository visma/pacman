package isma.games;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class PacmanDesktop {


    public static void main(String[] args) {
        int fps = 60;
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 480;
        config.height = 800;

//        config.width = 800;
//        config.height = 600;

        //config.fullscreen=true;

        config.vSyncEnabled = false;
        config.foregroundFPS = fps;
        config.backgroundFPS = fps;
        new LwjglApplication(new PacmanGame(), config);
    }
}
