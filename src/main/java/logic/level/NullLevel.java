package logic.level;

import controller.Game;
import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class NullLevel extends Observable implements Level {

    List<Brick>bricks;
    String name;
    int scoreLevel;

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
