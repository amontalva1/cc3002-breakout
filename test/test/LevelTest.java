package test;

import controller.Game;
import logic.brick.Brick;
import logic.level.NullLevel;
import logic.level.PlayableLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class LevelTest {

    PlayableLevel noMetalLevel;
    PlayableLevel onlyGlassLevel;
    NullLevel nullLevel;

    @Before
    public void setUp(){
        noMetalLevel = new PlayableLevel("Test No Metal Level", 10, 0.5, 0, 0);
        onlyGlassLevel = new PlayableLevel("Test only Glass", 10, 1, 0, 0);
        nullLevel = new NullLevel();
    }

    @Test
    public void nullLevelTest(){
        assertFalse(nullLevel.isPlayableLevel());
        assertEquals(nullLevel, nullLevel.getNextLevel());
        assertEquals(new ArrayList<Brick>(), nullLevel.getBricks());
        assertEquals("", nullLevel.getName());
        assertTrue(!nullLevel.hasNextLevel());
        assertTrue(nullLevel.getNumberOfBricks() == 0);
        assertTrue(nullLevel.getPoints() == 0);
    }

    @Test
    public void playableNoMetalLevel(){
        assertEquals(10 , noMetalLevel.getNumberOfBricks());
        assertEquals("Test No Metal Level" , noMetalLevel.getName());
        assertEquals(0 ,noMetalLevel.getCurrentScoreLevel());
        assertFalse(noMetalLevel.getNextLevel().isPlayableLevel());
        assertTrue(noMetalLevel.isPlayableLevel());
    }

    @Test
    public void playableOnlyGlassLevel(){
        assertEquals(10 , onlyGlassLevel.getNumberOfBricks());
        assertEquals("Test only Glass" , onlyGlassLevel.getName());
        assertEquals(0 , onlyGlassLevel.getCurrentScoreLevel());
        assertEquals(10*50, onlyGlassLevel.getPoints());
        assertTrue(onlyGlassLevel.isPlayableLevel());
        assertFalse(noMetalLevel.getNextLevel().isPlayableLevel());
        onlyGlassLevel.setNextLevel(noMetalLevel);
        assertTrue(onlyGlassLevel.getNextLevel().isPlayableLevel());
        assertEquals("Test No Metal Level", onlyGlassLevel.getNextLevel().getName());
        List<Brick> bricks = onlyGlassLevel.getBricks();
        for(Brick b:bricks){
            b.hit();
        }
        assertEquals(10*50, onlyGlassLevel.getCurrentScoreLevel());

    }



}
