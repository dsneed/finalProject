package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import Game.CollisionManager;
import Game.Game;
import Game.Projectile;
import Game.ProjectileManager;
import Game.Sprite;


public class MapLoadTests {
	private static Sprite sprite;
	private static Game game;
	private static CollisionManager collision = new CollisionManager(null, null, null, null, null);
	private static ProjectileManager projectileManager;
	private static Projectile projectile;
	
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 34;
	
	@BeforeClass
	public static void setUp() {
		game = new Game("TestLevel.csv");
	}

	@Test
	public void testLevelDimensions() {
		
		//TODO: Test to see the board is expected size
		assertEquals(NUM_COLUMNS, game.getNumColumns());
		assertEquals(NUM_ROWS, game.getNumRows());
		int totalCells = game.getNumRows() * game.getNumColumns();
		assertEquals(680, totalCells);
	}
	
	@Test
	public void testWallLocations() {
		//TODO: Test locations of some specific walls
		assertTrue(game.isWallAt(0,0));
		assertTrue(game.isWallAt(1,0));
		assertTrue(game.isWallAt(0,1));
		assertTrue(game.isWallAt(4,26));
		
		//TODO: Test the locations of the non-walls
		assertFalse(game.isWallAt(2,2));
		assertFalse(game.isWallAt(10,10));
		assertFalse(game.isWallAt(15,12));
		assertFalse(game.isWallAt(4,27));
		
		//TODO: Test number of total walls
		assertEquals(game.getNumWalls(), 129); // This was the number of walls I counted
	}
	
	@Test
	public void projectilePhysics(){
		//TODO: Test if projectile moves properly through map
		assertTrue(collision.isWallHit());
		
		//TODO: Test if projectile hits enemy
		assertTrue(collision.isEnemyHit());
		
	}

}
