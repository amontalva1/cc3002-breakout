package logic.brick;
import logic.level.Level;
import logic.level.PlayableLevel;

import java.util.Observable;

/**
 * WoodenBrick is a Brick Class that implements every method on Brick Interface
 * @author Antonio Montalva
 */
public class WoodenBrick extends Observable implements Brick {

    private int hitLeft;


    public WoodenBrick(){
        hitLeft = 3;
    }

    public WoodenBrick(PlayableLevel level){
        hitLeft = 3;
        addObserver(level);
    }

    @Override
    public void hit() {
        if(!isDestroyed()){
            hitLeft--;
            if(isDestroyed()){
                destroyed();
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return hitLeft==0;
    }

    @Override
    public int getScore() {
        return 200;
    }

    @Override
    public int remainingHits() {
        return hitLeft;
    }

    /**
     * Method that notify the Observers on Brick Destruction, and pass the Score of the Brick to the Observer
     */
    public void destroyed(){
        setChanged();
        notifyObservers(getScore());
    }
}
