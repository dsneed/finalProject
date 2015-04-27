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
	private static CollisionManager collision = new CollisionManager(null, null, null, null);
	private static ProjectileManager projectileManager;
	private static Projectile projectile;
	
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 34;
	
	@BeforeClass
	public static void setUp() {
		game = new Game("TestLevel.csv", null, null, null, null, null);
	}

	@Test
	public void testLevelDimensions() {
		
		//Test to see the board is expected size
		assertEquals(NUM_COLUMNS, game.getMap().getNumColumns());
		assertEquals(NUM_ROWS, game.getMap().getNumRows());
		int totalCells = game.getMap().getNumRows() * game.getMap().getNumColumns();
		assertEquals(680, totalCells);
	}
	
	@Test
	public void testWallLocations() {
		//Test locations of some specific walls
		assertTrue(game.getMap().isWallAt(0,0));
		assertTrue(game.getMap().isWallAt(1,0));
		assertTrue(game.getMap().isWallAt(0,1));
		assertTrue(game.getMap().isWallAt(4,26));
		
		//Test the locations of the non-walls
		assertFalse(game.getMap().isWallAt(2,2));
		assertFalse(game.getMap().isWallAt(10,10));
		assertFalse(game.getMap().isWallAt(15,12));
		assertFalse(game.getMap().isWallAt(4,27));
		
	}
	
	@Test
	public void projectilePhysics(){
		//TODO: Test if projectile moves properly through map
		assertTrue(collision.isWallHit());
		
		//TODO: Test if projectile hits enemy
		assertTrue(collision.isEnemyHit());
		
	}

}
