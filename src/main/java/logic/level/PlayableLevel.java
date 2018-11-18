package logic.level;

import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

import java.util.*;

public class PlayableLevel implements Level, Observer {

    private List<Brick> bricks;
    int scoreLevel;
    Level next;

    public PlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        bricks = new ArrayList<>();
        next = new NullLevel();
        scoreLevel = 0;
        Random r = new Random(seed);
        for(int i = 0; i<numberOfBricks; i++){
            if(r.nextDouble() < probOfGlass){
                bricks.add(new GlassBrick(this));
            }else{
                bricks.add(new WoodenBrick(this));
            }
            if(r.nextDouble() < probOfMetal){
                bricks.add(new MetalBrick(this));
            }
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getNumberOfBricks() {
        return bricks.size();
    }

    @Override
    public List<Brick> getBricks() {
        return bricks;
    }

    @Override
    public Level getNextLevel() {
        return next;
    }

    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    @Override
    public boolean hasNextLevel() {
        return next.isPlayableLevel();
    }

    @Override
    public int getPoints() {
        return scoreLevel;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        if(this.next.isPlayableLevel()){
            addPlayingLevel(next);
        }else{
            next = level;
        }
        return null;
    }

    @Override
    public void setNextLevel(Level level) {
        if(next.isPlayableLevel()){
            //this = (PlayableLevel) next;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            scoreLevel += (int) arg;
            bricks.remove((Brick) o);
        }
    }
}
