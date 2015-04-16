package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class MapLoadTests {
	private static Level level;
	public static final int NUM_ROWS = 19;
	public static final int NUM_COLUMNS = 33;
	
	@BeforeClass
	public static void setUp() {
		TrajectoryGame game = new TrajectoryGame("TestLevel.csv");
		game.loadConfigFiles();
		level = game.getLevel();
	}

	@Test
	public void testLevelDimensions() {
		assertEquals(NUM_ROWS, level.getNumRows());
		assertEquals(NUM_COLUMNS, level.getNumColumns());
		int totalCells = level.getNumRows() * level.getNumColumns();
		assertEquals(627, totalCells);
	}
	
	@Test
	public void testWallLocations() {
		assertEquals()	//TODO: add number of total walls
		//TODO: Test locations of some specific walls
	}

}
