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

    public static boolean crossTile(TiledMapWrapper map, Target actor, Target nextPosition) {
        int currTileX = (int) actor.getCenter().getX() / map.getTileWidth();
        int nextTileX = (int) nextPosition.getCenter().getX() / map.getTileWidth();
        int currTileY = (int) actor.getCenter().getY() / map.getTileHeight();
        int nextTileY = (int) nextPosition.getCenter().getY() / map.getTileHeight();
        if (currTileX != nextTileX || currTileY != nextTileY) {
//            System.out.printf("tile crossed | old : %s => new : %s\n", TargetUtils.stringify(actor), TargetUtils.stringify(nextPosition));
            return true;
        }
        float moduloTileX = actor.getCenter().getX() % map.getTileWidth();
        float moduloTileY = actor.getCenter().getY() % map.getTileHeight();
        if (moduloTileX == 0 && moduloTileY == 0){
//            System.out.printf("just on tile | old : %s => new : %s\n", TargetUtils.stringify(actor), TargetUtils.stringify(nextPosition));
            return true;
        }
        //System.out.printf("tile not crossed | old : %s => new : %s\n", TargetUtils.stringify(actor), TargetUtils.stringify(nextPosition));
        return false;
    }
}
