package logic.brick;
import java.util.Observable;
import logic.level.Level;
import logic.level.PlayableLevel;


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

    public void destroyed(){
        setChanged();
        //System.out.println("Se rompio un bloque aaaah!! " + getScore());
        notifyObservers(String.valueOf(getScore()));
    }
}