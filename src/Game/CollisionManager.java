package Game;

import java.util.ArrayList;

public class CollisionManager {
	private boolean enemyHit = false; 
	private boolean wallHit = false;
	private boolean projectileToWall = false;
	private boolean projectileToEnemy = false;
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
	
	//Checks Player intersection with Walls
	public boolean isWallHit() {
		for(Wall w: walls) {
			if(player.intersects(w))
					wallHit = true;
		}
		return wallHit;
	}
	
	//Checks Player intersection with Enemies
	public boolean isEnemyHit() {
		for(Enemy e: enemies) {
			if(player.intersects(e))
				enemyHit = true;
		}
		return enemyHit;
	}

	//Checks Projectile intersection with Walls
	public boolean checkProjectileToWall(Projectile p) {
		for(Wall w: walls) {
			if(player.intersects(w))
					projectileToWall = true;
		}
		return projectileToWall;
	}
	
	//Checks Projectile intersection with Enemies
	public boolean checkProjectileToEnemy(Projectile p) {
		for(Enemy e: enemies) {
			if(player.intersects(e))
				projectileToEnemy = true;
		}
		return projectileToEnemy;
	}

}
