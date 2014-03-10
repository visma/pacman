package isma.games.pacman.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameBoard extends Actor {

    private final BitmapFont font;
    private String message;

    public GameBoard() {
        font = new BitmapFont(Gdx.files.internal("arcade_12.fnt"));

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (message != null) {
            font.draw(batch, message, 88, 128);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
