package logic.level;

import controller.Game;
import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * NullLevel is the Null implementation of Level that allows to
 * define the behavior of a Null Object, helping us to avoid comparisons with Null
 * @author Antonio Montalva
 */
public class NullLevel extends Observable implements Level {

    private List<Brick>bricks;
    private String name;
    private int scoreLevel;

    /**
     * NullLevel constructor
     */
    public NullLevel(){
        bricks = new ArrayList<Brick>();
        name = "";
        scoreLevel = 0;
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
        return this;
    }

    @Override
    public boolean isPlayableLevel() {
        return false;
    }

    @Override
    public boolean hasNextLevel() {
        return false;
    }

    @Override
    public int getPoints() {
        return scoreLevel;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        return null;
    }

    @Override
    public void setNextLevel(Level level) {

    }

    @Override
    public void setObserver(Game game) {
        addObserver(game);
    }

}
