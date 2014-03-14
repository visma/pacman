package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import isma.games.pacman.core.assets.Assets;

import static isma.games.pacman.core.actors.Fruit.FruitEnum;

public class FruitPoints extends Actor {
    private boolean alive;
    private final Array<TextureRegion> textures = new Array<TextureRegion>();
    private Fruit fruit;

    public FruitPoints(Fruit fruit) {
        this.fruit = fruit;
        setVisible(false);

        Texture t = Assets.TEXTURE_FRUIT_POINTS;
        TextureRegion[][] regions = TextureRegion.split(t,
                t.getWidth(),
                t.getHeight() / FruitEnum.values().length);

        for (int i = 0; i < FruitEnum.values().length; i++) {
            textures.add(regions[i][0]);

        }
    }


    @Override
    public final void draw(Batch batch, float parentAlpha) {
        int index = 0;
        for (FruitEnum fruitEnum : FruitEnum.values()) {
            if (fruitEnum == fruit.getCurrentFruit()) {
                break;
            }
            index++;
        }
        batch.draw(textures.get(index), fruit.getCenter().x, fruit.getCenter().y);
    }
}
