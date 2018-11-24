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
   // Level onlyGlassLevel2;
    Level level;

    @Before
    public void setUp(){
        hw2 = new HomeworkTwoFacade();
        onlyGlassLevel = hw2.newLevelWithBricksNoMetal("Only Glass", 10, 1, 0);
       // onlyGlassLevel2 = hw2.newLevelWithBricksNoMetal("Only Glass", 10, 1, 0);
        onlyWoodLevel = hw2.newLevelWithBricksNoMetal("Only Wood", 10, 0, 0);
        level = hw2.newLevelWithBricksFull("Level", 20, 0.5, 0.1, 0);
    }

    @Test
    public void gameCreation(){
        hw2.setCurrentLevel(onlyGlassLevel);
        assertTrue(hw2.hasCurrentLevel());
        assertEquals("Only Glass", hw2.getLevelName());
        assertTrue(hw2.numberOfBricks() == 10);
        assertFalse(hw2.hasNextLevel());
        hw2.addPlayingLevel(onlyWoodLevel);
        assertTrue(hw2.hasNextLevel());
        assertEquals("Only Wood", hw2.game.getCurrentLevel().getNextLevel().getName());
        hw2.game.setNextLevel(onlyWoodLevel);
        assertEquals("Only Wood", hw2.game.getCurrentLevel().getNextLevel().getName());
        assertTrue(hw2.hasNextLevel());

        assertTrue(hw2.hasNextLevel());
        hw2.goNextLevel();
        assertTrue(hw2.hasCurrentLevel());
        assertFalse(hw2.hasNextLevel());
        assertEquals(10, hw2.numberOfBricks());

    }

    @Test
    public void gameplayTest(){
        hw2.setCurrentLevel(onlyGlassLevel);
        hw2.dropBall();
        assertEquals(2, hw2.getBallsLeft());
        hw2.game.setBalls(3);
        assertEquals(3, hw2.getBallsLeft());
        assertEquals(500, hw2.getLevelPoints());
        assertTrue(hw2.numberOfBricks() == 10);
        for(Brick b:hw2.getBricks()){
            b.hit();
        }
        assertTrue(hw2.numberOfBricks() == 0);
        assertEquals(500, hw2.getCurrentPoints());
        assertFalse(hw2.isGameOver());
        assertTrue(hw2.winner());
    }

    @Test
    public void longerGame(){
        hw2.setCurrentLevel(onlyGlassLevel);
        hw2.addPlayingLevel(onlyWoodLevel);
        for(Brick b:hw2.getBricks()){
            b.hit();
        }
        assertFalse(hw2.winner());
        for(Brick b:hw2.getBricks()){
            b.hit();
            b.hit();
            b.hit();
        }
        assertEquals(2500, hw2.getCurrentPoints());
        assertTrue(hw2.winner());
    }

}
