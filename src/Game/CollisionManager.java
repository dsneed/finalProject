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
	private void isWallHit() {
		for(Wall w: walls) {
			if(player.intersects(w)) {
				player.setIsBlocked(true);
			}
		}
	}
	
	//Checks Player intersection with Enemies
	private void isEnemyHit() {
		for(Enemy e: enemies) {
			if(player.intersects(e)) {
				player.setIsBlocked(true);
			}
		}
	}

	//Checks Projectile intersection with Walls
	private void checkProjectileToWall() {
		if(!walls.isEmpty()) {
			for(Wall w: walls) {
				for(Projectile p : projectileManager.getProjectiles()) {
					if(p.intersects(w)) {
						p.setIsBlocked(true);
					}
				}
			}
		}
	}

	//Checks Projectile intersection with Enemies
	private void checkProjectileToEnemy() {
		for(Enemy e: enemies) {
			for(Projectile p : projectileManager.getProjectiles()) {
				if(p.intersects(e)) {
					p.setIsBlocked(true);
					e.setDead(true);
				}
			}
		}
	}

	public void Update() {
		checkProjectileToEnemy();
		checkProjectileToWall();
		isEnemyHit();
		//isWallHit();
	}
}
