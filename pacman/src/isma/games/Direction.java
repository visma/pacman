package isma.games;

public enum Direction {
    EAST,
    SOUTH,
    WEST,
    NORTH;


    public Direction opposite() {
        switch (this) {
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
        }
        throw new RuntimeException("unreachable statement");
    }

    public Direction nextOnClockwise() {
        switch (this) {
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            case NORTH:
                return EAST;
        }
        throw new RuntimeException("unreachable statement");
    }

    public boolean isHorizontal() {
        return this == WEST || this == EAST;
    }

    public boolean isVertical() {
        return this == NORTH || this == SOUTH;
    }
}
