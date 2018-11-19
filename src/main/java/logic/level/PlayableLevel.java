package logic.level;

import controller.Game;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

import java.util.*;

public class PlayableLevel extends Observable implements Level, Observer {

    private List<Brick> bricks;
    String name;
    int scoreLevel;
    int currentScoreLevel;
    Level next;

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
        //System.out.println("Se rompio un bloque!!");

        if(arg instanceof String){
            currentScoreLevel += Integer.valueOf((String)arg);
            //bricks.remove((Brick) o);
            setChanged();
            notifyObservers((String)arg);
            //brickDestroyed((int) arg);
        }
    }

    public void brickDestroyed(int brickScore){
        notifyObservers(brickScore);
    }
}
