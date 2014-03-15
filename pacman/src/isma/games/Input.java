package isma.games;

import static com.badlogic.gdx.Input.Keys;

public enum Input {
    UP(Keys.UP),
    DOWN(Keys.DOWN),
    LEFT(Keys.LEFT),
    RIGHT(Keys.RIGHT),
    ESCAPE(Keys.ESCAPE),
    BACK(Keys.BACK),
    UNHANDLED(-1);
    private int keyCode;

    Input(int keyCode) {
        this.keyCode = keyCode;
    }

    public static Input get(int keyCode) {
        for (Input input : Input.values()) {
            if (input.keyCode == keyCode) {
                return input;
            }
        }
        return UNHANDLED;
    }

}
