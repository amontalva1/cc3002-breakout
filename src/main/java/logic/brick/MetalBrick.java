package logic.brick;

import logic.level.Level;

import java.util.Observable;

public class MetalBrick extends Observable implements Brick {

    private int hitLeft;

    public MetalBrick(Level level){
        hitLeft = 10;
        addObserver(level);
    }

    @Override
    public void hit() {
        hitLeft--;
        if(hitLeft == 0){
            destroyed();
        }
    }

    @Override
    public boolean isDestroyed() {
        return hitLeft == 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int remainingHits() {
        return hitLeft;
    }

    public void destroyed(){
        notifyObservers(getScore());
    }
}
