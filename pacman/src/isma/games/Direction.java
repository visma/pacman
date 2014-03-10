package isma.games;

public enum Direction {
    EAST,
    SOUTH,
    WEST,
    NORTH;

    Direction() {
    }


    //TODO Doublon a éliminer entre Point et Position sur nextPosition
    public Point nextPosition(Point position) {
        switch (this) {
            case EAST:
                return new Point(position.x + 1, position.y);
            case SOUTH:
                return new Point(position.x, position.y - 1);
            case WEST:
                return new Point(position.x - 1, position.y);
            case NORTH:
                return new Point(position.x, position.y + 1);
        }
        return null;
    }

    public Point previousPosition(Point position) {
        switch (this) {
            case EAST:
                return new Point(position.x - 1, position.y);
            case SOUTH:
                return new Point(position.x, position.y + 1);
            case WEST:
                return new Point(position.x + 1, position.y);
            case NORTH:
                return new Point(position.x, position.y - 1);
        }
        return null;
    }

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
