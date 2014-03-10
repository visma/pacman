package isma.games;

import java.util.HashMap;
import java.util.Map;

class TiledPropertiesCache {
    private Map<InnerKey, Map<Point, String>> cache = new HashMap<InnerKey, Map<Point, String>>();

    void put(int layerIndex, int x, int y, String key, String value) {
        InnerKey cacheKey = new InnerKey(layerIndex, key);
        Map<Point, String> valuesAt = cache.get(cacheKey);
        if (valuesAt == null) {
            valuesAt = new HashMap<Point, String>();
            cache.put(cacheKey, valuesAt);
        }
        valuesAt.put(new Point(x, y), value);
    }

    Point getPositionOfUniqueValue(int layerIndex, String key, String uniqueValue) {
        Map<Point, String> valuesAt = cache.get(new InnerKey(layerIndex, key));
        for (Point point : valuesAt.keySet()) {
            if (valuesAt.get(point).equals(uniqueValue)) {
                return point;
            }
        }
        return null;
    }

    class InnerKey {
        final int layerIndex;
        final String key;

        InnerKey(int layerIndex, String key) {
            this.layerIndex = layerIndex;
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof InnerKey)) return false;

            InnerKey innerKey = (InnerKey) o;

            if (layerIndex != innerKey.layerIndex) return false;
            if (key != null ? !key.equals(innerKey.key) : innerKey.key != null) return false;

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
