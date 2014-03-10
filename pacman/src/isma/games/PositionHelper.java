package isma.games;


import java.util.ArrayList;
import java.util.List;

import static isma.games.Direction.*;


public class PositionHelper {

    //TODO doublon a éliminer
    public static Direction getReverseDirection(Direction direction) {
        if (direction == EAST) {
            return WEST;
        } else if (direction == WEST) {
            return EAST;
        } else if (direction == SOUTH) {
            return NORTH;
        } else if (direction == NORTH) {
            return SOUTH;
        }
        throw new RuntimeException("not a valid direction : " + direction);
    }

    public static List<Direction> getOrientation(Point origin, Point reference) {
        List<Direction> directions = new ArrayList<Direction>();

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

