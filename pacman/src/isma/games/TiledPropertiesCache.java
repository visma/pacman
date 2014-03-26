package isma.games;

import com.badlogic.gdx.utils.ArrayMap;

public class TiledPropertiesCache {
    ArrayMap<Key, ArrayMap<Point, String>> cache = new ArrayMap<Key, ArrayMap<Point, String>>();

    void put(int layerIndex, int x, int y, String key, String value) {
        Key cacheKey = new Key(layerIndex, key);
        ArrayMap<Point, String> valuesAt = cache.get(cacheKey);
        if (valuesAt == null) {
            valuesAt = new ArrayMap<Point, String>();
            cache.put(cacheKey, valuesAt);
        }
        valuesAt.put(PointCache.get(x, y), value);
    }

    Point getPositionOfUniqueValue(int layerIndex, String key, String uniqueValue) {
        ArrayMap<Point, String> valuesAt = cache.get(new Key(layerIndex, key));
        for (Point point : valuesAt.keys()) {
            if (valuesAt.get(point).equals(uniqueValue)) {
                return point;
            }
        }
        return null;
    }

    public ArrayMap<Key, ArrayMap<Point, String>> getCache() {
        return cache;
    }

    public static class Key {
        final int layerIndex;
        final String key;

        public Key(int layerIndex, String key) {
            this.layerIndex = layerIndex;
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key) o;

            if (layerIndex != key.layerIndex) return false;
            if (this.key != null ? !this.key.equals(key.key) : key.key != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = layerIndex;
            result = 31 * result + (key != null ? key.hashCode() : 0);
            return result;
        }
    }
}
