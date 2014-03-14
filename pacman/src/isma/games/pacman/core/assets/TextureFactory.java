package isma.games.pacman.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

import isma.games.Direction;

import static com.badlogic.gdx.graphics.g2d.TextureRegion.split;
import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;

public class TextureFactory {

    public ArrayMap<Direction, Animation> buildAliveActorDefautAnimations(String name) {
        //TODO pas de new Texture ici !!!
        Texture texture = new Texture(Gdx.files.internal(Assets.getAliveActorSpriteSheet(name)));
        final int FRAME_DURATION = 8;
        TextureRegion[][] textureRegions = split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 2);
        ArrayMap<Direction, Animation> animations = new ArrayMap<Direction, Animation>();
        animations.put(EAST, new Animation(FRAME_DURATION, textureRegions[0][0], textureRegions[1][0]));
        animations.put(WEST, new Animation(FRAME_DURATION, textureRegions[0][1], textureRegions[1][1]));
        animations.put(NORTH, new Animation(FRAME_DURATION, textureRegions[0][2], textureRegions[1][2]));
        animations.put(SOUTH, new Animation(FRAME_DURATION, textureRegions[0][3], textureRegions[1][3]));
        return animations;
    }

    public Animation[] buildFrightenedGhostAnimation() {
        Texture texture = Assets.TEXTURE_GHOST_FRIGHTENED;
        final int FRAME_DURATION = 8;
        TextureRegion[][] textureRegions = split(texture,
                texture.getWidth() / 2,
                texture.getHeight() / 2);
        Animation anim1 = new Animation(FRAME_DURATION, textureRegions[0][0], textureRegions[1][0]);
        Animation anim2 = new Animation(FRAME_DURATION, textureRegions[0][1], textureRegions[1][1]);
        return new Animation[]{anim1, anim2};
    }

    public Animation buildPacmanDeathAnimation() {
        Texture texture = Assets.TEXTURE_PACMAN_DEATH;
        final int FRAME_DURATION = 6;
        TextureRegion[][] textureRegions = split(texture,
                texture.getWidth() / 12, texture.getHeight());
        return new Animation(FRAME_DURATION, textureRegions[0]);
    }

    public Animation buildDotAnimation(boolean energizer) {
        if (energizer) {
            Texture spriteSheet = Assets.TEXTURE_ENERGIZER;
            final int FRAME_DURATION = 30;
            TextureRegion[][] textureRegions = split(spriteSheet, spriteSheet.getWidth() / 2, spriteSheet.getHeight());
            return new Animation(FRAME_DURATION, textureRegions[0]);
        } else {
            Texture spriteSheet = Assets.TEXTURE_DOT;
            final int FRAME_DURATION = 1;
            TextureRegion[][] textureRegions = split(spriteSheet, spriteSheet.getWidth(), spriteSheet.getHeight());
            return new Animation(FRAME_DURATION, textureRegions[0]);
        }
    }

    public ArrayMap<Direction, Animation> buildNakedGhostAnimation() {
        Texture texture = Assets.TEXTURE_GHOST_NAKED;
        final int FRAME_DURATION = 1;
        TextureRegion[][] textureRegions = split(texture,
                texture.getWidth() / 4, texture.getHeight());
        ArrayMap<Direction, Animation> animations = new ArrayMap<Direction, Animation>();
        animations.put(EAST, new Animation(FRAME_DURATION, textureRegions[0][0]));
        animations.put(WEST, new Animation(FRAME_DURATION, textureRegions[0][1]));
        animations.put(NORTH, new Animation(FRAME_DURATION, textureRegions[0][2]));
        animations.put(SOUTH, new Animation(FRAME_DURATION, textureRegions[0][3]));
        return animations;
    }


}
