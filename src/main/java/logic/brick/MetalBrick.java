package logic.brick;

import logic.level.Level;
import logic.level.PlayableLevel;

import java.util.Observable;

public class MetalBrick extends Observable implements Brick {

    private int hitLeft;

    public MetalBrick(){
        hitLeft = 10;
    }

    public MetalBrick(PlayableLevel level){
        hitLeft = 10;
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
        setChanged();
        //System.out.println("Se rompio un bloque aaaah!! " + getScore());
        notifyObservers(String.valueOf(getScore()));
    }}
