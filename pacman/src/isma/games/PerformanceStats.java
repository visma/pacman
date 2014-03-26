package isma.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PerformanceStats {
    private long startTime;
    private Array<Integer> fpsArray = new Array<Integer>();
    private long iaCostNanoTime = 0;

    public PerformanceStats() {
        startTime = TimeUtils.nanoTime();
    }

    public void storeFpsPerSecond () {
        if (TimeUtils.nanoTime() - startTime > 1000000000) {
            fpsArray.add(Gdx.graphics.getFramesPerSecond());
            startTime = TimeUtils.nanoTime();
        }
    }

    public int getPerSecondAverage(){
        int sum = 0;
        for (Integer fpsPerSecond : fpsArray) {
            sum += fpsPerSecond;
        }
        int average = sum / fpsArray.size;
        System.out.println("total average = " + average);
        return average;
    }

    public void reset() {
        fpsArray.clear();
    }

    public int getIACostInMillis() {
        return (int) (iaCostNanoTime / (1000 * 1000));
    }

    public void addIACost(long nano) {
        iaCostNanoTime += nano;
    }
}
