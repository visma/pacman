package isma.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ArrayMap;

public abstract class GameConfiguration {
    private ArrayMap<String, String> properties = new ArrayMap<String, String>();

    public void load(){
        FileHandle conf = Gdx.files.internal("game.properties");
        String content = conf.readString();
        String[] lines = content.split(System.getProperty("line.separator"));

        for (String line : lines) {
            String[] columns = line.split("=");
            if (columns.length != 2) {
                continue;
            }
            String key = columns[0].trim();
            String value = columns[1].trim();
            properties.put(key, value);
        }
    }

    protected int getInt(String prop) {
        return Integer.parseInt(properties.get(prop));
    }

    protected float getFloat(String prop) {
        return Float.parseFloat(properties.get(prop));
    }


    protected long getLong(String prop) {
        return Long.parseLong(properties.get(prop));
    }
    protected boolean getBoolean(String prop) {
        return Boolean.parseBoolean(properties.get(prop));
    }
}
