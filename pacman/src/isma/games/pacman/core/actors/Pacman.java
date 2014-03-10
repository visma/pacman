package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Pacman extends AliveActor {
    private final Animation deathAnim;
    private PacmanState state;
    private TextureRegion lastTextureRegion;

    public enum PacmanState {
        ALIVE,
        DYING
    }

    Pacman(float speed) {
        super(ActorConstants.PACMAN.id, speed);
        deathAnim = textureFactory.buildPacmanDeathAnimation();
    }

    @Override
    protected TextureRegion getTextureRegionToDraw() {
        switch (state) {
            case ALIVE:
                if (!stopped) {
                    lastTextureRegion = animations.get(currentDirection).getKeyFrame(frame, true);
                }
                return lastTextureRegion;
            case DYING:
                return deathAnim.getKeyFrame(frame, false);
        }
        throw new RuntimeException("state null ?");
    }

    public void setState(PacmanState state) {
        this.state = state;
        if (state == PacmanState.DYING){
            frame = 0;
        }
    }
}
