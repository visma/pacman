package isma.games;


import com.badlogic.gdx.utils.Array;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;


public class PositionHelper {

    public static Array<Direction> getOrientation(Point origin, Point reference) {
        Array<Direction> directions = new Array<Direction>();

        if (reference.x < origin.x) {
            directions.add(WEST);
        } else if (reference.x > origin.x) {
            directions.add(EAST);
        }
        if (reference.y > origin.y) {
            directions.add(NORTH);
        } else if (reference.y < origin.y) {
            directions.add(SOUTH);
        }
        return directions;
    }


}

