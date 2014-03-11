package isma.games.pacman.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AliveActorTest {
    @Test
    public void getCenter() throws Exception {
        AliveActor actor = new InnerActor();
        actor.setX(10);
        actor.setY(10);

        Rectangle center = actor.getCenter();
        Rectangle expected = new Rectangle(10 + 4, 10 + 4, 8, 8);
        assertDoubleEquals(expected.x, center.x);
        assertDoubleEquals(expected.y, center.y);
        assertDoubleEquals(expected.width, center.width);
        assertDoubleEquals(expected.height, center.height);
    }

    private class InnerActor extends AliveActor {
        protected InnerActor() {
            super("test", 1);
        }

        @Override
        protected TextureRegion getTextureRegionToDraw() {
            return null;
        }

    }

    protected void assertDoubleEquals(double expected, double actual) {
        assertEquals(expected, actual, 0.0000001);
    }
}
