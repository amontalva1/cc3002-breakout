package logic.level;

import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;

public class NullLevel implements Level {

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

}
