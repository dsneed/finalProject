package Game;

import java.util.ArrayList;

public class CollisionManager {
	private boolean enemyHit = false; 
	private boolean wallHit = false;
	private ArrayList<Wall> walls;
	private ArrayList<Target> targets;
	private ArrayList<Enemy> enemies;
	private Player player;
	
	public CollisionManager(ArrayList<Wall> walls, ArrayList<Target> targets, 
			ArrayList<Enemy> enemies, Player player){
		this.walls = walls;
		this.targets = targets;
		this.enemies = enemies;
		this.player = player;
	}
	
	public boolean isWallHit() {
		for(Wall w: walls) {
			
		}
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
