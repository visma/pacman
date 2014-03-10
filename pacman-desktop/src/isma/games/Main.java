package isma.games;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "pacman";
		//cfg.useGL20 = false;
		cfg.width = 224;
		cfg.height = 288;
		
		new LwjglApplication(new PacmanGame(), cfg);
	}
}
