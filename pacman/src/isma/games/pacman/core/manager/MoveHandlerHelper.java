package isma.games.pacman.core.manager;

import com.badlogic.gdx.math.Rectangle;
import isma.games.SimpleTarget;
import isma.games.Target;
import isma.games.TiledMapHelper;
import isma.games.TiledMapWrapper;
import isma.games.pacman.core.actors.AliveActor;

public class MoveHandlerHelper {
    private MoveHandlerHelper() {
    }

    public static Target nextCenterPosition(TiledMapWrapper map, AliveActor actor, float remainingLen) {
        SimpleTarget nextCenterPosition = new SimpleTarget(actor.getCenter());
        switch (actor.getCurrentDirection()) {
            case EAST:
                nextCenterPosition.x += remainingLen;
                break;
            case SOUTH:
                nextCenterPosition.y -= remainingLen;
                break;
            case WEST:
                nextCenterPosition.x -= remainingLen;
                break;
            case NORTH:
                nextCenterPosition.y += remainingLen;
                break;
        }
        Rectangle beforeBoundsCorrection = nextCenterPosition.getCenter();
        TiledMapHelper.handleOutOfBounds(map, beforeBoundsCorrection);
        nextCenterPosition.x = beforeBoundsCorrection.x;
        nextCenterPosition.y = beforeBoundsCorrection.y;
        return nextCenterPosition;
    }
}
