package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Game.Game;
import Game.Sprite;
import Game.Wall;


public class MapLoadTests {
	private Sprite sprite = new Sprite();
	private static Game game;
	private Wall wall;
	
	public static final int NUM_ROWS = 19;
	public static final int NUM_COLUMNS = 33;
	
	@BeforeClass
	public static void setUp() {
		Game game = new Game("TestLevel.csv");
		//sprite = new Sprite();
	}

	@Test
	public void testLevelDimensions() {
		assertEquals(NUM_ROWS, sprite.getNumRows());
		assertEquals(NUM_COLUMNS, sprite.getNumColumns());
		int totalCells = sprite.getNumRows() * sprite.getNumColumns();
		assertEquals(627, totalCells);
	}
	
	@Test
	public void testWallLocations() {
		//TODO: Test locations of some specific walls
		//assertTrue(wall.isWall(0,0));
		//assertTrue(wall.isWall(0,1));
		
		//TODO: add number of total walls
		assertEquals(sprite.getNumWalls(), 125); // This was the number of walls I counted
	}

}
