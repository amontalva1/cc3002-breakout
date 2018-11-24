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
    private int balls;
    private int totalScore;
    private boolean winner = false;

    /**
     * Game constructor, needs as argument a positive Integer which represents the ammount of balls
     * that can be used to play
     * @param balls a positive integer
     */
    public Game(int balls) {
        currentLevel = new NullLevel();
        totalScore = 0;
        this.balls = balls;
    }

    /**
     * Method that returns the ammount of balls left in the game
     * @return an integer value
     */
    public int getBalls(){
        return balls;
    }

    /**
     * Sets the balls ammount to n
     * @param n integer value
     */
    public void setBalls(int n){
        balls = n;
    }

    /**
     * Drops the ammount of balls of the game by one
     * and returns the ammount of balls after the drop
     * @return an integer value
     */
    public int dropBalls(){
        if(balls>0){
            balls--;
        }
        return getBalls();
    }



    /**
     * Method that returns the total Score for the game so far
     * @return integer value
     */
    public int getTotalScore(){
        return totalScore;
    }

    /**
     * Method that returns the current Level of the game
     * @return Level object
     */
    public Level getCurrentLevel(){
        return currentLevel;
    }

    /**
     * Method that creates a PlayableLevel object
     * @param name String value, name of the level to create
     * @param numberOfBricks Integer value, ammount of Bricks to create on the Level
     * @param probOfGlass Double value, probability of one of the Bricks to create to be GlassBrick type
     * @param probOfMetal Double value, probability per Brick to create to generate one extra MetalBrick brick type
     * @param seed Integer value, seed to generate constant random numbers for testing purposes
     * @return Level type object
     */
    public Level createPlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        return new PlayableLevel(name, numberOfBricks, probOfGlass, probOfMetal, seed);
    }

    /**
     * Method that adds a Level to the game, to the end of the Levels already in the game
     * @param level
     */
    public void addPlayableLevel(Level level){
        if(!currentLevel.isPlayableLevel()){
            setCurrentLevel(level);
        }else{
            currentLevel.addPlayingLevel(level);
        }
    }

    /**
     * Method that changes the Current Level of the game to the Level level
     * @param level a Level object
     */
    public void setCurrentLevel(Level level){
        currentLevel = level;
        currentLevel.setObserver(this);
    }

    /**
     * Method that sets the next level of the game to level
     * @param level Level object
     */
    public void setNextLevel(Level level){
        this.currentLevel.setNextLevel(level);
    }

    /**
     * Method that changes the current level of the game to the next one
     */
    public void goNextLevel(){
        setCurrentLevel(currentLevel.getNextLevel());
    }

    /**
     * Method that returns the boolean winner value, that represents whether the game has a winner or not
     * @return boolean value
     */
    public boolean winner() {
        return winner;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            totalScore+=(int) arg;
            if(((int)arg) == 0){
                setBalls(getBalls()+1);
            }
            if(((PlayableLevel) o).getCurrentScoreLevel() == ((PlayableLevel) o).getPoints()){
                if(!currentLevel.getNextLevel().isPlayableLevel()){
                    winner = true;
                }
                goNextLevel();
            }
        }
    }
}
