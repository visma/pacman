package isma.games.pacman.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import isma.games.pacman.core.assets.Assets;

import static isma.games.pacman.core.assets.Assets.getAliveActorSpriteSheet;

public class GameBoard extends Actor {
    private final BitmapFont font;
    private final TextureRegion liveTexture;
    private String message;
    private int lives;
    private long score;
    private final Fruit fruit;

    public GameBoard(int lives, Fruit fruit) {
        this.lives = lives;
        this.fruit = fruit;

        font = Assets.FONT_ARCADE_12;
        String spriteSheet = getAliveActorSpriteSheet(ActorConstants.PACMAN.id);
        //TODO pas de new Texture ici !!!
        liveTexture = TextureRegion.split(new Texture(Gdx.files.internal(spriteSheet)), 16, 16)[0][0];
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (message != null) {
            font.draw(batch, message, 88, 128);
        }
        font.draw(batch, "score : " + score, 16, 275);
        for (int i = 0; i < lives; i++) {
            batch.draw(liveTexture, (i * 20), 0);
        }
        batch.draw(fruit.getCurrentTexture(), 208, 0);
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
