package test;

import controller.Game;
import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import logic.level.Level;
import logic.level.PlayableLevel;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class GameTest {

    HomeworkTwoFacade hw2;
    Level onlyGlassLevel;
    Level onlyWoodLevel;
    Level level;

    @Before
    public void setUp(){
        hw2 = new HomeworkTwoFacade();
        onlyGlassLevel = hw2.newLevelWithBricksNoMetal("Only Glass", 10, 1, 0);
        onlyWoodLevel = hw2.newLevelWithBricksNoMetal("Only Wood", 10, 0, 0);
        level = hw2.newLevelWithBricksFull("Level", 20, 0.5, 0.1, 0);
    }

    @Test
    public void gameCreation(){
        hw2.setCurrentLevel(onlyGlassLevel);
        assertTrue(hw2.hasCurrentLevel());
        assertTrue(hw2.numberOfBricks() == 10);
        assertFalse(hw2.hasNextLevel());
        hw2.addPlayingLevel(onlyWoodLevel);
        assertTrue(hw2.hasNextLevel());
        hw2.goNextLevel();
        assertTrue(hw2.hasCurrentLevel());
        assertFalse(hw2.hasNextLevel());
        assertEquals(10, hw2.numberOfBricks());
    }

    @Test
    public void gameplayTest(){
        hw2.setCurrentLevel(onlyGlassLevel);
        assertTrue(hw2.numberOfBricks() == 10);
        for(Brick b:hw2.getBricks()){
            b.hit();
        }
        assertTrue(hw2.numberOfBricks() == 0);
        assertEquals(500, hw2.getCurrentPoints());
    }

}
