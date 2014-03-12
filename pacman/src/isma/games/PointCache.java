package isma.games;


public class PointCache {
    private static int width = -1;
    private static int height = -1;

    private static Point[] cache;

    public static void init(int width, int height) {
        PointCache.width = width;
        PointCache.height = height;
        cache = new Point[width * height];
    }

    public static Point get(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            //Out of cache space
            return new Point(x, y);
        }
        int index = x + (y * width);
        Point point = cache[index];
        if (point == null) {
            point = new Point(x, y);
            cache[index] = point;
        }
        return point;
    }
}
