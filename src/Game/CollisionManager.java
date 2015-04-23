package Game;

import java.util.ArrayList;

public class CollisionManager {
	private boolean enemyHit = false; 
	private boolean wallHit = false;
	private ArrayList<Wall> walls;
	private ArrayList<Target> targets;
	private ArrayList<Enemy> enemies;
	private Player player;
	
	public CollisionManager(){
		
	}
	
	public boolean isWallHit() {
		return wallHit;
	}
	
	public boolean isEnemyHit() {
		return enemyHit;
	}

	public void checkPlayerToEnemy() {
		// TODO Auto-generated method stub
		
	}

	public void checkProjectileToWall(Projectile p) {
		// TODO Auto-generated method stub
		
	}

}
