package isma.games;

import com.badlogic.gdx.math.Vector2;

import static isma.games.Direction.EAST;
import static isma.games.Direction.NORTH;
import static isma.games.Direction.SOUTH;
import static isma.games.Direction.WEST;

public class Point {
    //Unique instance / point : finals modifiers
    public final int x;
    public final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public float dst(Point other) {
        return new Vector2(this.x, this.y).dst(new Vector2(other.x, other.y));
    }

    public Point getVector(Point other) {
        return PointCache.get(other.x - x, other.y - y);
    }

    public Point add(Point other) {
        return PointCache.get(x + other.x, y + other.y);
    }


    public Point onLeft() {
        return PointCache.get(x - 1, y);
    }

    public Point onRight() {
        return PointCache.get(x + 1, y);
    }

    public Point onTop() {
        return PointCache.get(x, y + 1);
    }

    public Point onBottom() {
        return PointCache.get(x, y - 1);
    }


    public Point onNext(Direction direction) {
        switch (direction) {
            case EAST:
                return onRight();
            case SOUTH:
                return onBottom();
            case WEST:
                return onLeft();
            case NORTH:
                return onTop();
        }
        return null;
    }

    public Point onPrevious(Direction direction) {
        switch (direction) {
            case EAST:
                return onNext(WEST);
            case SOUTH:
                return onNext(NORTH);
            case WEST:
                return onNext(EAST);
            case NORTH:
                return onNext(SOUTH);
        }
        return null;
    }

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
//       return false;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
