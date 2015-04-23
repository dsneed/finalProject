package Game;

import java.util.ArrayList;

public class CollisionManager {
	private ArrayList<Wall> walls;
	private ArrayList<Enemy> enemies;
	private ProjectileManager projectileManager;
	private Player player;
	
	public CollisionManager(ArrayList<Wall> walls, ArrayList<Enemy> enemies, 
			Player player, ProjectileManager projectileManager){
		this.walls = walls;
		this.enemies = enemies;
		this.player = player;
		this.projectileManager = projectileManager;
	}
	
	//Checks Player intersection with Walls
	public boolean isWallHit() {
		for(Wall w: walls) {
			if(player.intersects(w)) {
				player.setIsBlocked(true);
				return true;
			}
		}
		return false;
	}
	
	//Checks Player intersection with Enemies
	public boolean isEnemyHit() {
		for(Enemy e: enemies) {
			if(player.intersects(e)) {
				player.setIsBlocked(true);
				return true;
			}
		}
		return false;
	}

	//Checks Projectile intersection with Walls
	public boolean checkProjectileToWall() {
		for(Wall w: walls) {
			for(Projectile p : projectileManager.getProjectiles()) {
				if(p.intersects(w)) {
					p.setIsBlocked(true);
					return true;
				}
			}
		}
		return false;
	}
	
	//Checks Projectile intersection with Enemies
	public boolean checkProjectileToEnemy() {
		for(Enemy e: enemies) {
			for(Projectile p : projectileManager.getProjectiles()) {
				if(p.intersects(e)) {
					p.setIsBlocked(true);
					return true;
				}
			}
		}
		return false;
	}
}
