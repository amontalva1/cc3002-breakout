package controller;

import logic.level.Level;
import logic.level.NullLevel;
import logic.level.PlayableLevel;

import java.util.Observable;
import java.util.Observer;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 */
public class Game implements Observer {
    private Level currentLevel;
    int balls;
    int totalScore;
    boolean winner = false;
    public Game(int balls) {
        currentLevel = new NullLevel();
        totalScore = 0;
        this.balls = balls;
    }

    public int getBalls(){
        return balls;
    }

    public int dropBalls(){
        balls--;
        return getBalls();
    }

    public int getTotalScore(){
        return totalScore;
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    public Level createPlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        return new PlayableLevel(this,name, numberOfBricks, probOfGlass, probOfMetal, seed);
    }

    public void addPlayableLevel(Level level){
        if(!currentLevel.isPlayableLevel()){
            currentLevel = level;
        }else{
            currentLevel.addPlayingLevel(level);
        }
    }

    public void setNextLevel(){
        if(totalScore == currentLevel.getPoints() && currentLevel.hasNextLevel()){
            currentLevel = currentLevel.getNextLevel();
        }
    }

    /**
     * This method is just an example. Change it or delete it at wish.
     * <p>
     * Checks whether the game has a winner or not
     *
     * @return true if the game has a winner, false otherwise
     */
    public boolean winner() {
        return winner;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            totalScore+=(int)arg;
            if(((PlayableLevel) o).getCurrentScoreLevel() == ((PlayableLevel) o).getCurrentScoreLevel()){
                setNextLevel();
            }
        }
    }
}
