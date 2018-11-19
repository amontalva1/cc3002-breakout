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

    public Game(int balls) {
        currentLevel = new NullLevel();
        totalScore = 0;
        this.balls = balls;
    }

    public int getBalls(){
        return balls;
    }

    public void setBalls(int n){
        balls = n;
    }

    public int dropBalls(){
        if(balls>0){
            balls--;
        }
        return getBalls();
    }

    public int increaseBalls(){
        if(balls > 0){
            balls++;
        }
        return getBalls();
    }

    public int getTotalScore(){
        return totalScore;
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    public Level createPlayableLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        return new PlayableLevel(name, numberOfBricks, probOfGlass, probOfMetal, seed);
    }

    public void addPlayableLevel(Level level){
        if(!currentLevel.isPlayableLevel()){
            setCurrentLevel(level);
        }else{
            currentLevel.addPlayingLevel(level);
        }
    }

    public void setCurrentLevel(Level level){
        currentLevel = level;
        currentLevel.setObserver(this);
    }

    public void setNextLevel(Level level){
        this.currentLevel.setNextLevel(level);
    }

    public void goNextLevel(){
        setCurrentLevel(currentLevel.getNextLevel());
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
        if(arg instanceof String){
            //System.out.println("Se elimino un ladrillo puntaje total +=" + (String) arg);
            totalScore+=Integer.valueOf((String)arg);
            //System.out.println(totalScore);
            if(Integer.valueOf((String)arg) == 0){
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
