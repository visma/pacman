package isma.games.pacman.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor {
    private final BitmapFont font;
    private final String text;
    private final float x;
    private final float y;

    public TextActor(BitmapFont font, String text, float x, float y) {
        this.font = font;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public TextActor(BitmapFont font, String text, int y) {
        this.font = font;
        this.text = text;
        BitmapFont.TextBounds multiLineBounds = font.getMultiLineBounds(text);
        this.x = (Gdx.graphics.getWidth() - multiLineBounds.width) / 2;
        this.y = y;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, text, x, y);
    }


}
