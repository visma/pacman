package isma.games.test;

import com.badlogic.gdx.math.Rectangle;
import isma.games.Target;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TargetMockFactory {
    private TargetMockFactory() {
    }

    public static Target mockTarget(float x, float y, float dimension) {
        Rectangle center = new Rectangle(x, y, dimension, dimension);
        Target t = mock(Target.class);
        when(t.getCenter()).thenReturn(center);
        return t;
    }
}
