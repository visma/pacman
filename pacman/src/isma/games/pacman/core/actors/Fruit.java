package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.pacman.core.assets.Assets;

import static isma.games.pacman.core.actors.Fruit.FruitEnum.CHERRY;

public class Fruit extends Food {
    private static final int DEFAULT_WIDTH = 16;
    private static final int DEFAULT_HEIGHT = 16;

    public enum FruitEnum {
        CHERRY(100),
        STRAWBERRY(300),
        ORANGE(500),
        APPLE(700),
        MELON(1000),
        GALAXIAN(2000),
        BELL(3000),
        KEY(5000);

        public final int points;

        private FruitEnum(int points) {
            this.points = points;
        }
    }

    private ArrayMap<FruitEnum, TextureRegion> textures = new ArrayMap<FruitEnum, TextureRegion>();
    private FruitEnum currentFruit;


    public Fruit() {
        currentFruit = CHERRY;
        alive = false;
        Texture t = Assets.TEXTURE_FRUITS;
        TextureRegion[][] textureRegions = TextureRegion.split(t, t.getWidth(), t.getHeight() / FruitEnum.values().length);
        int i = 0;
        for (FruitEnum fruitEnum : FruitEnum.values()) {
            textures.put(fruitEnum, textureRegions[i++][0]);
        }
    }

    @Override
    public final void draw(Batch batch, float parentAlpha) {
        if (alive) {
            batch.draw(textures.get(currentFruit),
                    getX() * Assets.configuration.getScaleRatio(),
                    getY() * Assets.configuration.getScaleRatio());
        }
    }
    public Rectangle getCenter() {
        return new Rectangle(
                getX() + DEFAULT_WIDTH / 4,
                getY() + DEFAULT_HEIGHT / 4,
                DEFAULT_WIDTH / 2,
                DEFAULT_HEIGHT / 2);
    }

    public FruitEnum getCurrentFruit() {
        return currentFruit;
    }

    TextureRegion getCurrentTexture(){
        return textures.get(currentFruit);
    }

    public void setCurrentFruit(FruitEnum currentFruit) {
        this.currentFruit = currentFruit;
    }
}
