package test;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class BrickTest {

    WoodenBrick woodenBrick;
    GlassBrick glassBrick;
    MetalBrick metalBrick;

    @Before
    public void setUp(){
        woodenBrick = new WoodenBrick();
        glassBrick = new GlassBrick();
        metalBrick = new MetalBrick();
    }

    @Test
    public void bricksHit(){
        assertEquals(50, glassBrick.getScore());
        assertEquals(200, woodenBrick.getScore());
        assertEquals(0, metalBrick.getScore());
        assertEquals(1, glassBrick.remainingHits());
        assertEquals(3, woodenBrick.remainingHits());
        assertEquals(10, metalBrick.remainingHits());
        glassBrick.hit();
        woodenBrick.hit();
        metalBrick.hit();
        assertEquals(0, glassBrick.remainingHits());
        assertEquals(2, woodenBrick.remainingHits());
        assertEquals(9, metalBrick.remainingHits());
        assertTrue(glassBrick.isDestroyed());
        assertTrue(!woodenBrick.isDestroyed());
        assertTrue(!metalBrick.isDestroyed());
        glassBrick.hit();
        woodenBrick.hit();
        metalBrick.hit();
        assertEquals(0, glassBrick.remainingHits());
        assertEquals(1, woodenBrick.remainingHits());
        assertEquals(8, metalBrick.remainingHits());
        assertTrue(glassBrick.isDestroyed());
        assertTrue(!woodenBrick.isDestroyed());
        assertTrue(!metalBrick.isDestroyed());
        woodenBrick.hit();
        metalBrick.hit();
        woodenBrick.hit();
        metalBrick.hit();
        assertEquals(0, glassBrick.remainingHits());
        assertEquals(0, woodenBrick.remainingHits());
        assertEquals(6, metalBrick.remainingHits());
        assertTrue(glassBrick.isDestroyed());
        assertTrue(woodenBrick.isDestroyed());
        assertTrue(!metalBrick.isDestroyed());
        metalBrick.hit();
        metalBrick.hit();
        metalBrick.hit();
        metalBrick.hit();
        metalBrick.hit();
        metalBrick.hit();
        assertTrue(glassBrick.isDestroyed());
        assertTrue(woodenBrick.isDestroyed());
        assertTrue(metalBrick.isDestroyed());
    }
}
