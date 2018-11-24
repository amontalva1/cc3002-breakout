package logic.brick;
import java.util.Observable;
import logic.level.Level;
import logic.level.PlayableLevel;

/**
 * GlassBrick is a Brick Class that implements every method on Brick Interface
 * @author Antonio Montalva
 */
public class GlassBrick extends Observable implements Brick{

    private int hitLeft;

    public GlassBrick(){
        hitLeft = 1;
    }

    public GlassBrick(PlayableLevel level){
        hitLeft = 1;
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
        return 50;
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
