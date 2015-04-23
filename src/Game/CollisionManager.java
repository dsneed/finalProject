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
	private ProjectileManager projectileManager;
	private Player player;
	
	public CollisionManager(ArrayList<Wall> walls, ArrayList<Target> targets, 
			ArrayList<Enemy> enemies, Player player, ProjectileManager projectileManager){
		this.walls = walls;
		this.targets = targets;
		this.enemies = enemies;
		this.player = player;
		this.projectileManager = projectileManager;
	}
	
	//Checks Player intersection with Walls
	public boolean isWallHit() {
		for(Wall w: walls) {
			if(player.intersects(w)) {
				player.setIsBlocked(true);
				wallHit = true;
			}
		}
		return wallHit;
	}
	
	//Checks Player intersection with Enemies
	public boolean isEnemyHit() {
		for(Enemy e: enemies) {
			if(player.intersects(e)) {
				player.setIsBlocked(true);
				enemyHit = true;
			}
		}
		return enemyHit;
	}

	//Checks Projectile intersection with Walls
	public boolean checkProjectileToWall() {
		for(Wall w: walls) {
			for(Projectile p : projectileManager.getProjectiles()) {
				if(p.intersects(w)) {
					projectileToWall = true;
					p.setIsBlocked(true);
				}
			}
		}
		return projectileToWall;
	}
	
	//Checks Projectile intersection with Enemies
	public boolean checkProjectileToEnemy() {
		for(Enemy e: enemies) {
			for(Projectile p : projectileManager.getProjectiles()) {
				if(p.intersects(e)) {
					projectileToEnemy = true;
					p.setIsBlocked(true);
				}
			}
		}
		return projectileToEnemy;
	}
}
