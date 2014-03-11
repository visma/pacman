package isma.games.test;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import isma.games.pacman.core.actors.Dot;
import isma.games.pacman.core.actors.Pacman;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActorMockFactory {
    private ActorMockFactory() {
    }

    public static Pacman mockPacman(Vector2 centerPosition) {
        int centerSize = 8;
        Rectangle center = new Rectangle(centerPosition.x, centerPosition.y, centerSize, centerSize);
        Pacman pacman = mock(Pacman.class);
        when(pacman.getX()).thenReturn(centerPosition.x - 4);
        when(pacman.getY()).thenReturn(centerPosition.y - 4);
        when(pacman.getCenter()).thenReturn(center);
        return pacman;
    }

    public static Dot mockDot(Vector2 position, boolean alive, boolean energizer) {
        int centerSize = 8;
        Rectangle center = new Rectangle(position.x, position.y, centerSize, centerSize);
        Dot dot = mock(Dot.class);
        when(dot.getX()).thenReturn(position.x);
        when(dot.getY()).thenReturn(position.y);
        when(dot.getCenter()).thenReturn(center);
        when(dot.isAlive()).thenReturn(alive);
        when(dot.isEnergizer()).thenReturn(energizer);
        return dot;
    }

}
