package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Timer;
import java.util.TimerTask;

import isma.games.Direction;
import isma.games.math.SinusVariation;
import isma.games.math.TrigoFunctionFactory;
import isma.games.pacman.core.assets.Assets;

import static isma.games.pacman.core.actors.Ghost.GhostState.FRIGTHENED;
import static isma.games.pacman.core.actors.Ghost.GhostState.NAKED;
import static isma.games.pacman.core.actors.Ghost.GhostState.NORMAL;

public class Ghost extends AliveActor {
    private static final float FRIGHTENED_SPEED_COEFF = 0.6f;
    private static final float NAKED_SPEED_COEFF = 1.3f;
    private final Animation[] frightenedAnimation;
    private final ArrayMap<Direction, Animation> nakedAnimation;
    private GhostState state;
    private SinusVariation sinusVariation;
    private boolean fearBlinkMode = false;


    public enum GhostState {
        NORMAL,
        FRIGTHENED,
        NAKED
    }

    Ghost(String id, float speedGhost) {
        super(id, speedGhost);
        frightenedAnimation = textureFactory.buildFrightenedGhostAnimation();
        nakedAnimation = textureFactory.buildNakedGhostAnimation();
    }

    @Override
    protected TextureRegion getTextureRegionToDraw() {
        switch (state) {
            case NORMAL:
                return animations.get(currentDirection).getKeyFrame(frame, true);
            case FRIGTHENED:
                if (fearBlinkMode) {
                    return frightenedAnimation[sinusVariation.nextValue() > 0 ? 0 : 1].getKeyFrame(frame, true);
                } else {
                    return frightenedAnimation[1].getKeyFrame(frame, true);
                }
            case NAKED:
                return nakedAnimation.get(currentDirection).getKeyFrame(frame, true);
        }
        throw new RuntimeException("unreachable statement");
    }

    @Override
    public void die() {
        super.die();
        setState(NAKED);
    }

    public void setFrightened(boolean frightened) {
        if (frightened)
            setState(FRIGTHENED);
        else
            setState(NORMAL);
    }

    public void revive() {
        setState(NORMAL);
        //Hack for clyde if inside house)
        currentDirection = Direction.NORTH;
    }

    private void setState(GhostState state) {
        GhostState old = this.state;
        this.state = state;
        if (this.state != old) {
            for (WorldEventListener eventListener : eventListeners) {
                eventListener.onStateChanged(this, old, state);
            }
        }
        if (state == FRIGTHENED) {
            sinusVariation = TrigoFunctionFactory.buildSinus(0.2f, 0.00f, false, 1);
            fearBlinkMode = false;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    fearBlinkMode = true;
                }
            }, (3 * Assets.configuration.getFearBehaviorDuration() / 4));
        } else {
            sinusVariation = null;
            fearBlinkMode = false;
        }
    }

    public GhostState getState() {
        return state;
    }

    @Override
    public float getSpeed() {
        if (state == FRIGTHENED) {
            return super.getSpeed() * FRIGHTENED_SPEED_COEFF;
        } else if (state == NAKED) {
            return super.getSpeed() * NAKED_SPEED_COEFF;
        }
        return super.getSpeed();
    }
}
