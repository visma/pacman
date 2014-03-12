package isma.games.pacman.core.stages;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class MyGestureListener extends GestureDetector.GestureAdapter {
    private DirectionListener directionListener;

    public MyGestureListener(DirectionListener directionListener) {
        this.directionListener = directionListener;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("visma touchDown");
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("visma tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.println("visma longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("visma fling " + velocityX + " - " + velocityY);
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                directionListener.onRight();
            } else {
                directionListener.onLeft();
            }
        } else {
            if (velocityY > 0) {
                directionListener.onDown();
            } else {
                directionListener.onUp();
            }
        }
        return super.fling(velocityX, velocityY, button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        System.out.println("visma pan");
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        System.out.println("visma panStop");
        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){
        System.out.println("visma zoom");
        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
        System.out.println("visma pinch");
        return false;
    }
}