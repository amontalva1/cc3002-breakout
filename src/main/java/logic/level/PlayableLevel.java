package logic.level;

import controller.Game;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

import java.util.*;

/**
 * PlayableLevel Class is the implementation of a Level that can be played and has fields and method that allows so
 * @author Antonio Montalva
 */
public class PlayableLevel extends Observable implements Level, Observer {

    private List<Brick> bricks;
    private String name;
    private int scoreLevel;
    private int currentScoreLevel;
    private Level next;

    /**
     * PlayableLevel constructor
     * @param name String value, name of the level to create
     * @param numberOfBricks Integer value, ammount of Bricks to create on the Level
     * @param probOfGlass Double value, probability of one of the Bricks to create to be GlassBrick type
     * @param probOfMetal Double value, probability per Brick to create to generate one extra MetalBrick brick type
     * @param seed Integer value, seed to generate constant random numbers for testing purposes
     */
    public PlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        //addObserver(game);
        bricks = new ArrayList<>();
        next = new NullLevel();
        scoreLevel = 0;
        this.name = name;
        Random r = new Random(seed);
        for(int i = 0; i<numberOfBricks; i++){
            Double d = r.nextDouble();
            if(d <= probOfGlass){
                bricks.add(new GlassBrick(this));
                scoreLevel += 50;
            }else{
                bricks.add(new WoodenBrick(this));
                scoreLevel += 200;
            }
        }
        for(int i = 0; i<numberOfBricks; i++){
            Double d = r.nextDouble();
            if(d < probOfMetal){
                bricks.add(new MetalBrick(this));
            }
        }
    }

    /**
     * Method that returns the Score got in the Level so far
     * @return int value
     */
    public int getCurrentScoreLevel(){
        return currentScoreLevel;
    }

    @Override
    public String getName() {
        return name;
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

        Level aux = this;
        while (aux.getNextLevel().isPlayableLevel()) {
            aux = aux.getNextLevel();
        }
        aux.setNextLevel(level);
        return null;
    }







    @Override
    public void setNextLevel(Level level) {
        next = level;
    }

    @Override
    public void setObserver(Game game) {
        addObserver(game);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            currentScoreLevel += (int) arg;
            setChanged();
            notifyObservers(arg);
        }
    }

}
