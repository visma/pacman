package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import isma.games.PerformanceStats;
import isma.games.pacman.core.assets.Assets;
import isma.games.pacman.core.assets.TextureFactory;

import static isma.games.Direction.EAST;
import static isma.games.pacman.core.actors.ActorConstants.PACMAN;

public class GameBoard extends Actor {
    private final BitmapFont font;
    private final TextureRegion liveTexture;
    private String message;
    private int lives;
    private long score;
    private final Fruit fruit;
    public final PerformanceStats performanceStats;

    public GameBoard(int lives, Fruit fruit, PerformanceStats performanceStats) {
        this.lives = lives;
        this.fruit = fruit;
        this.performanceStats = performanceStats;

        font = Assets.FONT_ARCADE_12;
        font.setScale(Assets.configuration.getScaleRatio(), Assets.configuration.getScaleRatio());
        liveTexture = new TextureFactory().buildAliveActorDefautAnimations(PACMAN.id).get(EAST).getKeyFrame(0);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (message != null) {
            font.draw(batch, message,
                    88 * Assets.configuration.getScaleRatio(),
                    128 * Assets.configuration.getScaleRatio());
        }
        font.draw(batch, "ia cost " + performanceStats.getIACostInMillis() + "ms",
                100 * Assets.configuration.getScaleRatio(),
                275 * Assets.configuration.getScaleRatio());
        font.draw(batch, "score : " + score,
                16 * Assets.configuration.getScaleRatio(),
                275 * Assets.configuration.getScaleRatio());
        for (int i = 0; i < lives; i++) {
            batch.draw(liveTexture,
                    (i * 20) * Assets.configuration.getScaleRatio(),
                    0 * Assets.configuration.getScaleRatio());
        }
        batch.draw(fruit.getCurrentTexture(),
                208 * Assets.configuration.getScaleRatio(),
                0 * Assets.configuration.getScaleRatio());
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
