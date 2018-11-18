package logic.brick;
import java.util.Observable;
import logic.level.Level;
import logic.level.PlayableLevel;


public class GlassBrick extends Observable implements Brick{

    private int hitLeft;

    public GlassBrick(PlayableLevel level){
        hitLeft = 1;
        addObserver(level);
    }

    @Override
    public void hit() {
        hitLeft--;
        if(isDestroyed()){
            destroyed();
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

    public void destroyed(){
        notifyObservers(getScore());
    }
}
